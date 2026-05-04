package com.thedeathlycow.thirstful.mixin.common.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"
            )
    )
    private void replaceExhaustionWithThirst(
            FoodData instance,
            float exhaustion,
            Operation<Void> original,
            @Local(argsOnly = true) Player player
    ) {
        PlayerThirstComponent thirstData = PlayerThirstComponent.get(player);

        thirstData.removeThirstLevel(exhaustion / 8.0);

        if (thirstData.isDehydrated()) {
            original.call(instance, exhaustion);
        }
    }
}