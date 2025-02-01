package com.thedeathlycow.thirstful.mixin.common.item;

import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Inject(
            method = "finishUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z",
                    shift = At.Shift.AFTER
            )
    )
    private void afterConsume(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (user instanceof ServerPlayerEntity player) {
            ConsumeItemCallback.EVENT.invoker().onConsume(player, stack);
        }
    }
}