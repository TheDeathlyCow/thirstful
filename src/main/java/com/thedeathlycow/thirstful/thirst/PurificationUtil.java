package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public final class PurificationUtil {
    /**
     * Copes the disease, dirtiness, saltiness from the input stacks to the output, if they would be worse than the output's default
     */
    public static <T extends RecipeInput> void copy(T input, ItemStack output) {
        PollutantComponent combined = getCombinedPollutantsFromInput(input, output, false, false);
        output.set(TDataComponentTypes.POLLUTANTS, combined);
    }

    /**
     * Removes disease from the input stacks, and applies the dirtiness effects with the highest probability of all effects
     * applying to the output
     */
    public static <T extends RecipeInput> void pasteurize(T input, ItemStack output) {
        PollutantComponent combined = getCombinedPollutantsFromInput(input, output, false, true);
        output.set(TDataComponentTypes.POLLUTANTS, combined);
    }

    /**
     * Removes dirtiness from the input stacks, and applies the disease effects with the highest probability of all effects
     * applying to the output
     */
    public static <T extends RecipeInput> void filter(T input, ItemStack output) {
        PollutantComponent combined = getCombinedPollutantsFromInput(input, output, true, false);
        output.set(TDataComponentTypes.POLLUTANTS, combined);
    }


    /**
     * Removes dirtiness, disease, and saltiness from the input stacks.
     */
    public static <T extends RecipeInput> void distill(T input, ItemStack output) {
        PollutantComponent fallback = output.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);

        PollutantComponent combined = new PollutantComponent(
                Collections.emptyList(),
                Collections.emptyList(),
                false,
                disjunctionOrDefault(input, PollutantComponent::showInTooltip, fallback.showInTooltip())
        );

        output.set(TDataComponentTypes.POLLUTANTS, combined);
    }

    private static PollutantComponent getCombinedPollutantsFromInput(
            RecipeInput input,
            ItemStack output,
            boolean clearDirtiness,
            boolean clearDisease
    ) {
        PollutantComponent fallback = output.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);

        List<PollutantComponent.StatusEffectEntry> dirtinessEffects = clearDirtiness
                ? Collections.emptyList()
                : getHighestProbabilityEffectsList(input, PollutantComponent::dirtinessEffects, fallback.dirtinessEffects());

        List<PollutantComponent.StatusEffectEntry> diseaseEffects = clearDisease
                ? Collections.emptyList()
                : getHighestProbabilityEffectsList(input, PollutantComponent::diseaseEffects, fallback.diseaseEffects());

        return new PollutantComponent(
                dirtinessEffects,
                diseaseEffects,
                disjunctionOrDefault(input, PollutantComponent::salty, fallback.salty()),
                disjunctionOrDefault(input, PollutantComponent::showInTooltip, fallback.showInTooltip())
        );
    }

    private static boolean disjunctionOrDefault(
            RecipeInput recipeInput,
            Predicate<PollutantComponent> provider,
            boolean fallback
    ) {
        for (int i = 0; i < recipeInput.getSize(); i++) {
            ItemStack stack = recipeInput.getStackInSlot(i);
            PollutantComponent component = stack.get(TDataComponentTypes.POLLUTANTS);

            if (component != null && provider.test(component)) {
                return true;
            }
        }

        return fallback;
    }

    /**
     * Returns the effect list from the recipe input that has the highest probability that all effects will apply.
     *
     * @param recipeInput  Recipe input
     * @param effectGetter Provides the effect list from a stack
     * @return Returns a copy of an effect list provided by the {@code effectGetter}, or a copy of the fallback if nothing
     * in the input is polluted
     */
    private static List<PollutantComponent.StatusEffectEntry> getHighestProbabilityEffectsList(
            RecipeInput recipeInput,
            Function<PollutantComponent, List<PollutantComponent.StatusEffectEntry>> effectGetter,
            List<PollutantComponent.StatusEffectEntry> fallback
    ) {
        List<PollutantComponent.StatusEffectEntry> highestSoFar = null;
        float highestProbabilitySoFar = Float.NEGATIVE_INFINITY;

        for (int i = 0; i < recipeInput.getSize(); i++) {
            ItemStack stack = recipeInput.getStackInSlot(i);
            PollutantComponent component = stack.get(TDataComponentTypes.POLLUTANTS);

            if (component != null) {
                List<PollutantComponent.StatusEffectEntry> effects = effectGetter.apply(component);
                float probability = probabilityOfAllEffects(effects);

                if (probability > highestProbabilitySoFar) {
                    highestSoFar = effects;
                    highestProbabilitySoFar = probability;
                }
            }
        }

        return List.copyOf(highestSoFar != null ? highestSoFar : fallback);
    }

    private static float probabilityOfAllEffects(List<PollutantComponent.StatusEffectEntry> effects) {
        if (effects.isEmpty()) {
            return 0f;
        }

        float probability = 1.0f;
        for (PollutantComponent.StatusEffectEntry effect : effects) {
            probability *= effect.probability();
        }
        return probability;
    }

    private PurificationUtil() {

    }
}