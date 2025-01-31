package com.thedeathlycow.thirstful.effect;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

@FunctionalInterface
public interface AllowStatusEffectCallback {
    Event<AllowStatusEffectCallback> EVENT = EventFactory.createArrayBacked(
            AllowStatusEffectCallback.class,
            listeners -> (entity, effectInstance) -> {
                for (AllowStatusEffectCallback listener : listeners) {
                    TriState result = listener.canApplyEffect(entity, effectInstance);
                    if (result != TriState.DEFAULT) {
                        return result;
                    }
                }

                return TriState.DEFAULT;
            }
    );

    TriState canApplyEffect(LivingEntity entity, StatusEffectInstance effectInstance);
}
