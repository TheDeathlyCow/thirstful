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

package com.thedeathlycow.thirstful.mixin.common.block;

import net.minecraft.core.cauldron.CauldronInteraction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CauldronInteraction.class)
public interface CauldronInteractionMixin {
//    @WrapOperation(
//            method = "method_32220",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/item/ItemUsage;exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"
//            )
//    )
//    private static ItemStack polluteFilledWaterBottle(
//            ItemStack inputStack,
//            PlayerEntity player,
//            ItemStack outputStack,
//            Operation<ItemStack> original,
//            @Local(argsOnly = true) BlockPos pos
//    ) {
//        WaterCollection.pollutePlayerCollectedWater(outputStack, player, pos);
//        return original.call(inputStack, player, outputStack);
//    }
//
//    @ModifyExpressionValue(
//            method = "method_32222",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/component/type/PotionContentsComponent;matches(Lnet/minecraft/registry/entry/RegistryEntry;)Z"
//            )
//    )
//    private static boolean alwaysPlaceWaterCauldron(boolean original) {
//        return true;
//    }
}