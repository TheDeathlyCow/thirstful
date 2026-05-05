package com.thedeathlycow.thirstful.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TConventionalItemTags {
    public static final TagKey<Item> DRINK_CONTAINING_BOWL = key("drink_containing/bowl");

    private static TagKey<Item> key(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    private TConventionalItemTags() {

    }
}