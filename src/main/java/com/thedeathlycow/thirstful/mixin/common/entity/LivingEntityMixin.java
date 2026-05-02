package com.thedeathlycow.thirstful.mixin.common.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thedeathlycow.thirstful.effect.AllowStatusEffectCallback;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyReturnValue(
            method = "canBeAffected",
            at = @At("TAIL")
    )
    private boolean checkCanHaveStatusEffects(boolean original, MobEffectInstance effectInstance) {
        if (original) {
            TriState result = AllowStatusEffectCallback.EVENT.invoker().canApplyEffect(
                    (LivingEntity) (Object) this,
                    effectInstance
            );
            if (result != TriState.DEFAULT) {
                return result.get();
            }
        }
        return original;
    }
}