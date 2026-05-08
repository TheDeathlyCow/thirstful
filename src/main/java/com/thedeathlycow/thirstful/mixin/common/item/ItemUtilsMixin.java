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

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemUtils.class)
public abstract class ItemUtilsMixin {
    @WrapMethod(
            method = "createFilledResult(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/item/ItemStack;"
    )
    private static ItemStack polluteFilledOutput(ItemStack inputStack, Player player, ItemStack outputStack, boolean creativeOverride, Operation<ItemStack> original) {
        PollutantComponent inputPollution = inputStack.get(TDataComponentTypes.POLLUTANTS);
        if (inputPollution != null && WaterPollution.canCarryPollutants(outputStack)) {
            PollutantComponent existingOutput = outputStack.getOrDefault(
                    TDataComponentTypes.POLLUTANTS,
                    PollutantComponent.DEFAULT
            );
            outputStack.set(TDataComponentTypes.POLLUTANTS, inputPollution.mixWith(existingOutput));
        }

        return original.call(inputStack, player, outputStack, creativeOverride);
    }
}