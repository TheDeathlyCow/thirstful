package com.thedeathlycow.thirstful.item;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface StackCreationCallback {
    Event<StackCreationCallback> EVENT = EventFactory.createArrayBacked(
            StackCreationCallback.class,
            listeners -> stack -> {
                for (StackCreationCallback listener : listeners) {
                    listener.onCreated(stack);
                }
            }
    );

    void onCreated(ItemStack stack);
}