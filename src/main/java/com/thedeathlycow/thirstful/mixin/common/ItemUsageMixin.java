package com.thedeathlycow.thirstful.mixin.common;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemUsage.class)
public class ItemUsageMixin {
    @WrapMethod(
            method = "exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/item/ItemStack;"
    )
    private static ItemStack afterExchange(
            ItemStack inputStack,
            PlayerEntity player,
            ItemStack outputStack,
            boolean creativeOverride,
            Operation<ItemStack> original
    ) {
        WaterCollection.polluteWater(player, inputStack, outputStack);
        return original.call(inputStack, player, outputStack, creativeOverride);
    }
}