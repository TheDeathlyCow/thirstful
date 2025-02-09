package com.thedeathlycow.thirstful.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ThirstfulConventionalItemTags {
    /**
     * Drinks are defined as (1) consumable items that (2) use the
     * {@linkplain net.minecraft.util.UseAction#DRINK drink use action}, (3) can be consumed regardless of the
     * player's current hunger.
     *
     * <p>Drinks may provide nutrition and saturation, but are not required to do so.
     *
     * <p>Categories of drinks, such as Coffee, Tea, or Alcoholic drinks should be placed in a sub-tag, such as
     * {@code #c:drinks/coffee}, {@code #c:drinks/tea}, and {@code #c:drinks/alcohol}.
     *
     * <p>Specific types of drinks, such as Lattes, Green Tea, and Beer should go in sub-sub-tags using their regular name,
     * such as {@code #c:drinks/coffee/latte}, {@code #c:drinks/tea/green_tea}, and {@code #c:drinks/alcohol/beer}.
     */
    public static final TagKey<Item> DRINKS = register("drinks");
    /**
     * For consumable drinks that contain only water.
     */
    public static final TagKey<Item> WATER_DRINKS = register("drinks/water");
    /**
     * For consumable drinks that are generally watery (such as potions).
     */
    public static final TagKey<Item> WATERY_DRINKS = register("drinks/watery");
    public static final TagKey<Item> MILK_DRINKS = register("drinks/milk");
    public static final TagKey<Item> HONEY_DRINKS = register("drinks/honey");
    /**
     * For consumable drinks that are magic in nature and usually grant at least one
     * {@link net.minecraft.entity.effect.StatusEffect} when consumed.
     */
    public static final TagKey<Item> MAGIC_DRINKS = register("drinks/magic");
    /**
     * For drinks that always grant the {@linkplain net.minecraft.entity.effect.StatusEffects#BAD_OMEN Bad Omen} effect.
     */
    public static final TagKey<Item> OMINOUS_MAGIC_DRINKS = register("drinks/magic/ominous");
    /**
     * Plant based fruit and vegetable juices belong in this tag, for example apple juice and carrot juice.
     */
    public static final TagKey<Item> JUICE_DRINKS = register("drinks/juice");
    public static final TagKey<Item> APPLE_JUICE_DRINKS = register("drinks/juice/apple");
    public static final TagKey<Item> BEETROOT_JUICE_DRINKS = register("drinks/juice/beetroot");
    public static final TagKey<Item> CARROT_JUICE_DRINKS = register("drinks/juice/carrot");
    public static final TagKey<Item> MELON_JUICE_DRINKS = register("drinks/juice/melon");

    // Drink containing items
    /**
     * For non-empty buckets that are {@linkplain #DRINKS drinkable}.
     */
    public static final TagKey<Item> DRINK_CONTAINING_BUCKET = register("drink_containing/bucket");
    /**
     * For non-empty bottles that are {@linkplain #DRINKS drinkable}.
     */
    public static final TagKey<Item> DRINK_CONTAINING_BOTTLE = register("drink_containing/bottle");


    // custom tag for scorchful
    public static final TagKey<Item> CACTUS_JUICE_DRINKS = register("drinks/juice/cactus");


    public static TagKey<Item> register(String path) {
        return TagKey.of(RegistryKeys.ITEM, commonId(path));
    }

    public static Identifier commonId(String path) {
        return Identifier.of("c", path);
    }

    private ThirstfulConventionalItemTags() {
    }
}