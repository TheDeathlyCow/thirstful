package com.thedeathlycow.thirstful.mixin.common.block;

import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CauldronBlock.class)
public class CauldronBlockMixin {
    @ModifyArg(
            method = "precipitationTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
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
            method = "fillFromDripstone",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"
            ),
            index = 1
    )
    private BlockState polluteCauldronIfRaining(BlockState state, @Local(argsOnly = true) Fluid fluid) {
        if (fluid.isIn(FluidTags.WATER)) {
            return PollutedWaterCauldronBlock.fillFromDripstone(state);
        }
        return state;
    }
}