package com.thedeathlycow.thirstful.mixin.common;

import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import com.thedeathlycow.thirstful.item.WaterPollutor;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(
            method = "<init>(Lnet/minecraft/item/ItemConvertible;ILnet/minecraft/component/ComponentMapImpl;)V",
            at = @At("TAIL")
    )
    private void afterNewItemStack(ItemConvertible item, int count, ComponentMapImpl components, CallbackInfo ci) {
        ItemStackCreationCallback.EVENT.invoker().onCreated((ItemStack) (Object) this);
    }

    @Inject(
            method = "use",
            at = @At("TAIL")
    )
    private void afterUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        WaterPollutor.postUse(world, user, hand, cir.getReturnValue());
    }
}