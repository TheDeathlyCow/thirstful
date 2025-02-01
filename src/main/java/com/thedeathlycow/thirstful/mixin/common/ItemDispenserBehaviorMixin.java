package com.thedeathlycow.thirstful.mixin.common;

import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDispenserBehavior.class)
public class ItemDispenserBehaviorMixin {
    @Inject(
            method = "addStackOrSpawn",
            at = @At("HEAD")
    )
    private void polluteCollectedStacks(BlockPointer pointer, ItemStack stack, CallbackInfo ci) {
        BlockPos pos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));
        WaterCollection.polluteCollectedWater(stack, pointer.world(), pos);
    }
}