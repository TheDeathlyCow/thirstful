package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder {
    @Shadow
    public abstract UseAction getUseAction();

    @Shadow public abstract ItemStack copy();

    @Inject(
            method = "<init>(Lnet/minecraft/item/ItemConvertible;ILnet/minecraft/component/ComponentMapImpl;)V",
            at = @At("TAIL")
    )
    private void afterNewItemStack(ItemConvertible item, int count, ComponentMapImpl components, CallbackInfo ci) {
        ItemStackCreationCallback.EVENT.invoker().onCreated((ItemStack) (Object) this);
    }

    @WrapMethod(
            method = "finishUsing"
    )
    private ItemStack invokeConsumeItem(World world, LivingEntity user, Operation<ItemStack> original) {
        boolean wasConsumable = this.contains(DataComponentTypes.FOOD) || this.getUseAction() == UseAction.DRINK;
        ItemStack prior = this.copy();

        ItemStack result = original.call(world, user);

        if (wasConsumable) {
            ConsumeItemCallback.EVENT.invoker().onConsume(user, prior);
        }

        return result;
    }
}