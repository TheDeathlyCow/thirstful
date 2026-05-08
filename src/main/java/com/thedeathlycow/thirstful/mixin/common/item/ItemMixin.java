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

package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.item.DrinkTooltipComponent;
import com.thedeathlycow.thirstful.item.component.DrinkComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {
    @ModifyReturnValue(
            method = "getTooltipImage",
            at = @At("RETURN")
    )
    private Optional<TooltipComponent> addWaterTooltipData(
            Optional<TooltipComponent> original,
            @Local(argsOnly = true) ItemStack stack
    ) {
        DrinkComponent drink = DrinkComponent.getByTag(stack);

        if (original.isEmpty() && drink.water() > 0) {
            return Optional.of(new DrinkTooltipComponent(drink));
        }

        return original;
    }
}