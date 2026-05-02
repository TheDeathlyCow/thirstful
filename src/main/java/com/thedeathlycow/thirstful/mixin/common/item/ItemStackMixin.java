package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements DataComponentHolder {
    @Shadow
    public abstract UseAnim getUseAnimation();

    @Shadow public abstract ItemStack copy();

    @Inject(
            method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V",
            at = @At("TAIL")
    )
    private void afterNewItemStack(ItemLike item, int count, PatchedDataComponentMap components, CallbackInfo ci) {
        ItemStackCreationCallback.EVENT.invoker().onCreated((ItemStack) (Object) this);
    }

    @WrapMethod(
            method = "finishUsingItem"
    )
    private ItemStack invokeConsumeItem(Level world, LivingEntity user, Operation<ItemStack> original) {
        boolean wasConsumable = this.has(DataComponents.FOOD) || this.getUseAnimation() == UseAnim.DRINK;
        ItemStack prior = this.copy();

        ItemStack result = original.call(world, user);

        if (wasConsumable) {
            ConsumeItemCallback.EVENT.invoker().onConsume(user, prior);
        }

        return result;
    }
}