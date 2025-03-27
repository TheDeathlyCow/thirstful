package com.thedeathlycow.thirstful.item;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface ConsumeItemCallback {
    Event<ConsumeItemCallback> EVENT = EventFactory.createArrayBacked(
            ConsumeItemCallback.class,
            listeners -> (entity, stack) -> {
                for (ConsumeItemCallback listener : listeners) {
                    listener.onConsume(entity, stack);
                }
            }
    );

    void onConsume(LivingEntity entity, ItemStack stack);
}