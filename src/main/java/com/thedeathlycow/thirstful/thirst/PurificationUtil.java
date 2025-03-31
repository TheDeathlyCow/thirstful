package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SingleStackRecipeInput;

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
        output.set(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
        copyScorchfulDrinksComponent(input, output);
    }

    private static PollutantComponent getCombinedPollutantsFromInput(
            RecipeInput input,
            ItemStack output,
            boolean clearDirtiness,
            boolean clearDisease
    ) {
        PollutantComponent fallback = output.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);

        boolean dirty = !clearDirtiness && disjunctionOrDefault(input, PollutantComponent::dirty, fallback.dirty());
        boolean contaminated = !clearDisease && disjunctionOrDefault(input, PollutantComponent::contaminated, fallback.contaminated());

        return new PollutantComponent(
                dirty,
                contaminated,
                disjunctionOrDefault(input, PollutantComponent::salty, fallback.salty())
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

    private static <T extends RecipeInput> void copyScorchfulDrinksComponent(T input, ItemStack output) {
        if (input instanceof SingleStackRecipeInput singleInput && ModIntegration.isScorchfulLoaded()) {
            ScorchfulIntegration.copyDrinksToOutput(singleInput, output);
        }
    }

    private PurificationUtil() {

    }
}