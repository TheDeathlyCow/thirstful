package com.thedeathlycow.thirstful.mixin.common.item;

import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(
            method = "<init>(Lnet/minecraft/item/ItemConvertible;ILnet/minecraft/component/ComponentMapImpl;)V",
            at = @At("TAIL")
    )
    private void afterNewItemStack(ItemConvertible item, int count, ComponentMapImpl components, CallbackInfo ci) {
        ItemStackCreationCallback.EVENT.invoker().onCreated((ItemStack) (Object) this);
    }
}