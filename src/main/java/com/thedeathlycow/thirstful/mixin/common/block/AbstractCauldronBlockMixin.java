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

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBehavior;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {
    @Shadow
    @Final
    protected CauldronInteraction.InteractionMap interactions;

    @WrapMethod(
            method = "useItemOn"
    )
    private ItemInteractionResult fillEmptyCauldronOrCancel(
            ItemStack stack,
            BlockState state,
            Level world,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit,
            Operation<ItemInteractionResult> original
    ) {
        PollutantComponent pollution = stack.getOrDefault(
                TDataComponentTypes.POLLUTANTS,
                PollutantComponent.DEFAULT
        );

        if (pollution.clean()) {
            return original.call(stack, state, world, pos, player, hand, hit);
        }

        if (this.interactions.name().equals(CauldronInteraction.EMPTY.name())) {
            ItemInteractionResult result = original.call(stack, state, world, pos, player, hand, hit);

            PollutedWaterCauldronBehavior.replaceWithPollutedWaterCauldron(
                    pollution,
                    world.getBlockState(pos),
                    world,
                    pos
            );

            return result;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}