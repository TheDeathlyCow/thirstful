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

package com.thedeathlycow.thirstful.mixin.common.compat.farmersdelight.present;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

@Mixin(StoveBlockEntity.class)
public class StoveBlockEntityMixin {
    @WrapOperation(
            method = "cookAndOutputItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/CampfireCookingRecipe;getResult(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack pasteurizeResult(
            CampfireCookingRecipe instance,
            HolderLookup.Provider wrapperLookup,
            Operation<ItemStack> original,
            @Local(name = "stoveStack") ItemStack input
    ) {
        ItemStack result = original.call(instance, wrapperLookup);
        PurificationUtil.pasteurize(new SingleRecipeInput(input), result);
        return result;
    }
}