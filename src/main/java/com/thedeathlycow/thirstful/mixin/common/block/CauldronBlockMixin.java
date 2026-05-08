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

import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {
    @ModifyArg(
            method = "handlePrecipitation",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState polluteCauldronIfRaining(BlockState state, @Local(argsOnly = true) Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return PollutedWaterCauldronBlock.fillWithRain(state);
        }
        return state;
    }

    @ModifyArg(
            method = "receiveStalactiteDrip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState polluteCauldronIfRaining(BlockState state, @Local(argsOnly = true) Fluid fluid) {
        if (fluid.is(FluidTags.WATER)) {
            return PollutedWaterCauldronBlock.fillFromDripstone(state);
        }
        return state;
    }
}