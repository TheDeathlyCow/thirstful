package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SingleStackRecipeInput;

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
        copyScorchfulDrinksComponent(input, output);
    }

    /**
     * Removes disease from the input stacks, and applies the dirtiness effects with the highest probability of any effect
     * applying to the output
     */
    public static <T extends RecipeInput> void pasteurize(T input, ItemStack output) {
        PollutantComponent combined = getCombinedPollutantsFromInput(input, output, false, true);
        output.set(TDataComponentTypes.POLLUTANTS, combined);
        copyScorchfulDrinksComponent(input, output);
    }

    /**
     * Removes dirtiness from the input stacks, and applies the disease effects with the highest probability of any effect
     * applying to the output
     */
    public static <T extends RecipeInput> void filter(T input, ItemStack output) {
        PollutantComponent combined = getCombinedPollutantsFromInput(input, output, true, false);
        output.set(TDataComponentTypes.POLLUTANTS, combined);
        copyScorchfulDrinksComponent(input, output);
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
        copyScorchfulDrinksComponent(input, output);
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
                : getHighestProbabilityOfAnyEffectList(input, PollutantComponent::dirtinessEffects, fallback.dirtinessEffects());

        List<PollutantComponent.StatusEffectEntry> diseaseEffects = clearDisease
                ? Collections.emptyList()
                : getHighestProbabilityOfAnyEffectList(input, PollutantComponent::diseaseEffects, fallback.diseaseEffects());

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
     * Returns the effect list from the recipe input that has the highest probability that any effects will apply.
     *
     * @param recipeInput  Recipe input
     * @param effectGetter Provides the effect list from a stack
     * @return Returns a copy of an effect list provided by the {@code effectGetter}, or a copy of the fallback if nothing
     * in the input is polluted
     */
    private static List<PollutantComponent.StatusEffectEntry> getHighestProbabilityOfAnyEffectList(
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
                float probability = probabilityOfAnyEffect(effects);

                if (probability > highestProbabilitySoFar) {
                    highestSoFar = effects;
                    highestProbabilitySoFar = probability;
                }
            }
        }

        return List.copyOf(highestSoFar != null ? highestSoFar : fallback);
    }

    static float probabilityOfAnyEffect(List<PollutantComponent.StatusEffectEntry> effects) {
        if (effects.isEmpty()) {
            return 0f;
        }

        // calculates the probability of any the effects in the list applying by finding the inverse probability
        // of none of the effects occurring.

        float probability = 1.0f;
        for (PollutantComponent.StatusEffectEntry effect : effects) {
            probability *= 1 - effect.probability(); // probability of effect not applying
        }
        return 1 - probability;
    }

    private static <T extends RecipeInput> void copyScorchfulDrinksComponent(T input, ItemStack output) {
        if (input instanceof SingleStackRecipeInput singleInput && ModIntegration.isScorchfulLoaded()) {
            ScorchfulIntegration.copyDrinksToOutput(singleInput, output);
        }
    }

    private PurificationUtil() {

    }
}