package com.thedeathlycow.thirstful.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin {
    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/GlassBottleItem;fill(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
                    ordinal = 1
            )
    )
    private ItemStack polluteFilledWaterBottle(
            GlassBottleItem instance,
            ItemStack stack,
            PlayerEntity player,
            ItemStack outputStack,
            Operation<ItemStack> original,
            @Local BlockPos pos
    ) {
        WaterCollection.pollutePlayerCollectedWater(outputStack, player, pos);
        return original.call(instance, stack, player, outputStack);
    }
}