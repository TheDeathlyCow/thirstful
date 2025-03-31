package com.thedeathlycow.thirstful.mixin.common.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeveledCauldronBlock.class)
public class LeveledCauldronBlockMixin {
    @ModifyExpressionValue(
            method = "precipitationTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;cycle(Lnet/minecraft/state/property/Property;)Ljava/lang/Object;"
            )
    )
    private Object polluteCauldronIfRaining(Object original, @Local(argsOnly = true) Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return PollutedWaterCauldronBlock.fillWithRain((BlockState) original);
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "fillFromDripstone",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/property/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
            )
    )
    private Object polluteCauldronIfRaining(Object original, @Local(argsOnly = true) Fluid fluid) {
        if (fluid.isIn(FluidTags.WATER)) {
            return PollutedWaterCauldronBlock.fillFromDripstone((BlockState) original);
        }
        return original;
    }
}