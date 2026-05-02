package com.thedeathlycow.thirstful.mixin.common.block;

import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultDispenseItemBehavior.class)
public class ItemDispenserBehaviorMixin {
    @Inject(
            method = "addToInventoryOrDispense",
            at = @At("HEAD")
    )
    private void polluteCollectedStacks(BlockSource pointer, ItemStack stack, CallbackInfo ci) {
        BlockPos pos = pointer.pos().relative(pointer.state().getValue(DispenserBlock.FACING));
        WaterCollection.polluteCollectedWater(stack, pointer.level(), pos);
    }
}