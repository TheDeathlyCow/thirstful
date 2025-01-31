package com.thedeathlycow.thirstful.item;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface ItemStackCreationCallback {
    Event<ItemStackCreationCallback> EVENT = EventFactory.createArrayBacked(
            ItemStackCreationCallback.class,
            listeners -> stack -> {
                for (ItemStackCreationCallback listener : listeners) {
                    listener.onCreated(stack);
                }
            }
    );

    void onCreated(ItemStack stack);
}