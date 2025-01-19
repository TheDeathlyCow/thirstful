package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TItemTags {
    public static final TagKey<Item> DRINKS = key("drinks");

    private static TagKey<Item> key(String id) {
        return TagKey.of(RegistryKeys.ITEM, Thirstful.id(id));
    }

    private TItemTags() {

    }
}