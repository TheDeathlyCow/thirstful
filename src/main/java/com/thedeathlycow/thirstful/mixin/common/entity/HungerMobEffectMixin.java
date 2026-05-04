package com.thedeathlycow.thirstful.mixin.common.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.effect.HungerMobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerMobEffect.class)
public class HungerMobEffectMixin {
    @WrapOperation(
            method = "applyEffectTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"
            )
    )
    private void applyExhaustionDirectly(Player instance, float exhaustion, Operation<Void> original) {
        if (!instance.getAbilities().invulnerable && !instance.level().isClientSide()) {
            instance.getFoodData().addExhaustion(exhaustion);
        }
    }
}