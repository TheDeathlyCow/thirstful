package com.thedeathlycow.thirstful.mixin.common.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(
            method = "causeFoodExhaustion",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"
            )
    )
    private void wrapExhaustionWithThirst(FoodData instance, float exhaustion, Operation<Void> original) {
        Player self = (Player) (Object) this;
        PlayerThirstComponent thirstData = PlayerThirstComponent.get(self);

        if (!thirstData.isDehydrated()) {
            thirstData.removeThirstLevel(exhaustion / 4.0);
        } else {
            original.call(instance, exhaustion);
        }
    }
}