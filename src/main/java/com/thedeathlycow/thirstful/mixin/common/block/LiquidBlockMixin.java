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