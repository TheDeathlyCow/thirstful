package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.datagen.ThirstfulDataGenerator;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public TItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TItemTags.POLLUTED_CONSUMABLES)
                .addOptionalTag(TItemTags.SALTY_BY_DEFAULT)
                .addOptionalTag(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(TItemTags.DIRTY_BY_DEFAULT)
                .addOptionalTag(ConventionalItemTags.DRINKS)
                .addOptionalTag(ConventionalItemTags.POTIONS)
                .addOptionalTag(ConventionalItemTags.FOODS)
                .addOptionalTag(ConventionalItemTags.MILK_BUCKETS);

        getOrCreateTagBuilder(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(ConventionalItemTags.MILK_DRINKS)
                .addOptionalTag(ConventionalItemTags.RAW_MEAT_FOODS)
                .addOptionalTag(ConventionalItemTags.FOOD_POISONING_FOODS)
                .addOptionalTag(ConventionalItemTags.MILK_BUCKETS);

        getOrCreateTagBuilder(TItemTags.SALTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.DIRTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.IS_WATER)
                .addOptionalTag(ConventionalItemTags.WATERY_DRINKS)
                .addOptionalTag(ConventionalItemTags.WATER_DRINKS)
                .addOptionalTag(ConventionalItemTags.WATER_BUCKETS);
    }
}