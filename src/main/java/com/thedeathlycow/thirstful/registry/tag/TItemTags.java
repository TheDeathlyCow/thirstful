package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TItemTags {
    public static final TagKey<Item> DIRTY_BY_DEFAULT = key("polluted_consumables/dirty_by_default");
    public static final TagKey<Item> CONTAMINATED_BY_DEFAULT = key("polluted_consumables/contaminated_by_default");
    public static final TagKey<Item> SALTY_BY_DEFAULT = key("polluted_consumables/salty_by_default");
    public static final TagKey<Item> POLLUTED_CONSUMABLES = key("polluted_consumables");
    public static final TagKey<Item> WATERY_DRINKS = key("watery_drinks");

    private static TagKey<Item> key(String id) {
        return TagKey.of(RegistryKeys.ITEM, Thirstful.id(id));
    }

    private TItemTags() {

    }
}