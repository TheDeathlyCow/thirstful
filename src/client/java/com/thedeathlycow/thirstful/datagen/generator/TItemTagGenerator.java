package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> WATER_DRINKS = TagKey.of(RegistryKeys.ITEM, Thirstful.commonId("drinks/watery"));
    public static final TagKey<Item> MILK_DRINKS = TagKey.of(RegistryKeys.ITEM, Thirstful.commonId("drinks/milk"));
    public static final TagKey<Item> DRINKS = TagKey.of(RegistryKeys.ITEM, Thirstful.commonId("drinks"));

    public TItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TItemTags.POLLUTED_CONSUMABLES)
                .add(Items.HONEY_BOTTLE)
                .add(Items.OMINOUS_BOTTLE)
                .addOptionalTag(ConventionalItemTags.POTIONS)
                .addOptionalTag(ConventionalItemTags.FOODS)
                .addOptionalTag(DRINKS)
                .addOptionalTag(TItemTags.SALTY_BY_DEFAULT)
                .addOptionalTag(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(TItemTags.DIRTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.CONTAMINATED_BY_DEFAULT)
                .add(Items.MILK_BUCKET)
                .addOptionalTag(ConventionalItemTags.RAW_MEAT_FOODS)
                .addOptionalTag(ConventionalItemTags.FOOD_POISONING_FOODS)
                .addOptionalTag(MILK_DRINKS)
                .addOptionalTag(WATER_DRINKS);

        getOrCreateTagBuilder(TItemTags.SALTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.DIRTY_BY_DEFAULT);
    }
}