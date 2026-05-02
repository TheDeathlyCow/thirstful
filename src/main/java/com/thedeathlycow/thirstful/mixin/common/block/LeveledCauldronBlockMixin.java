package com.thedeathlycow.thirstful.mixin.common.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LayeredCauldronBlock.class)
public class LeveledCauldronBlockMixin {
    @ModifyExpressionValue(
            method = "handlePrecipitation",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;cycle(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Object;"
            )
    )
    private Object polluteCauldronIfRaining(Object original, @Local(argsOnly = true) Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return PollutedWaterCauldronBlock.fillWithRain((BlockState) original);
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "receiveStalactiteDrip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;"
            )
    )
    private Object polluteCauldronIfRaining(Object original, @Local(argsOnly = true) Fluid fluid) {
        if (fluid.is(FluidTags.WATER)) {
            return PollutedWaterCauldronBlock.fillFromDripstone((BlockState) original);
        }
        return original;
    }
}