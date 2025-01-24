package com.thedeathlycow.thirstful.item;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface ConsumeItemCallback {
    Event<ConsumeItemCallback> EVENT = EventFactory.createArrayBacked(
            ConsumeItemCallback.class,
            listeners -> (player, stack) -> {
                for (ConsumeItemCallback listener : listeners) {
                    listener.onConsume(player, stack);
                }
            }
    );

    void onConsume(ServerPlayerEntity player, ItemStack stack);
}