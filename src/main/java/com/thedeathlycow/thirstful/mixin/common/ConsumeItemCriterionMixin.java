package com.thedeathlycow.thirstful.mixin.common;

import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConsumeItemCriterion.class)
public class ConsumeItemCriterionMixin {
    @Inject(
            method = "trigger",
            at = @At("HEAD")
    )
    private void onItemConsumed(ServerPlayerEntity player, ItemStack stack, CallbackInfo ci) {
        // do not invoke for milk buckets so that listener-applied effects are not cleared
        if (!(stack.getItem() instanceof MilkBucketItem)) {
            ConsumeItemCallback.EVENT.invoker().onConsume(player, stack);
        }
    }
}