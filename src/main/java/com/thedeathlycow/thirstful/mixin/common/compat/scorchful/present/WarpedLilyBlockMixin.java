package com.thedeathlycow.thirstful.mixin.common.compat.scorchful.present;

import com.github.thedeathlycow.scorchful.block.NetherLilyBehaviours;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.item.WaterCollection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherLilyBehaviours.class)
public class WarpedLilyBlockMixin {
    @WrapOperation(
            method = "lambda$initialize$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemUsage;exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"
            )
    )
    private static ItemStack polluteNetherLily(
            ItemStack inputStack,
            PlayerEntity player,
            ItemStack outputStack,
            Operation<ItemStack> original,
            @Local(argsOnly = true) BlockPos pos
    ) {
        WaterCollection.pollutePlayerCollectedWater(outputStack, player, pos);
        return original.call(inputStack, player, outputStack);
    }
}