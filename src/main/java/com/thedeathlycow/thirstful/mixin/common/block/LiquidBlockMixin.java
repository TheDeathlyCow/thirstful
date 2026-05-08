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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
    @Shadow
    @Final
    protected FlowingFluid fluid;

    @ModifyReturnValue(
            method = "pickupBlock",
            at = @At("RETURN")
    )
    private ItemStack polluteCollectedWater(
            ItemStack original,
            @Nullable Player player,
            LevelAccessor world,
            BlockPos pos,
            BlockState state
    ) {
        if (!original.isEmpty() && player != null && this.fluid.is(FluidTags.WATER)) {
            WaterCollection.pollutePlayerCollectedWater(original, player, pos);
        }

        return original;
    }
}