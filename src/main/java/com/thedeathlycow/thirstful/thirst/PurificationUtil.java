/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;

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
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            PollutantComponent component = stack.get(TDataComponentTypes.POLLUTANTS);

            if (component != null && provider.test(component)) {
                return true;
            }
        }

        return fallback;
    }

    private static <T extends RecipeInput> void copyScorchfulDrinksComponent(T input, ItemStack output) {
        if (input instanceof SingleRecipeInput singleInput && ModIntegration.isScorchfulLoaded()) {
            ScorchfulIntegration.copyDrinksToOutput(singleInput, output);
        }
    }

    private PurificationUtil() {

    }
}