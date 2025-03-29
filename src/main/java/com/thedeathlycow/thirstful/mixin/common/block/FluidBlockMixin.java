package com.thedeathlycow.thirstful.mixin.common.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {
    @Shadow
    @Final
    protected FlowableFluid fluid;

    @ModifyReturnValue(
            method = "tryDrainFluid",
            at = @At("RETURN")
    )
    private ItemStack polluteCollectedWater(
            ItemStack original,
            @Nullable PlayerEntity player,
            WorldAccess world,
            BlockPos pos,
            BlockState state
    ) {
        if (!original.isEmpty() && player != null && this.fluid.isIn(FluidTags.WATER)) {
            WaterCollection.pollutePlayerCollectedWater(original, player, pos);
        }

        return original;
    }
}