package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.datagen.ThirstfulDataGenerator;
import com.thedeathlycow.thirstful.registry.ThirstfulConventionalItemTags;
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
        this.generateCommonDrinkTags(wrapperLookup);

        getOrCreateTagBuilder(TItemTags.POLLUTED_CONSUMABLES)
                .addOptionalTag(TItemTags.WATERY_DRINKS)
                .addOptionalTag(TItemTags.SALTY_BY_DEFAULT)
                .addOptionalTag(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(TItemTags.DIRTY_BY_DEFAULT)
                .addOptionalTag(ThirstfulConventionalItemTags.DRINKS)
                .addOptionalTag(ConventionalItemTags.POTIONS)
                .addOptionalTag(ConventionalItemTags.FOODS)
                .addOptionalTag(ConventionalItemTags.MILK_BUCKETS);

        getOrCreateTagBuilder(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(ThirstfulConventionalItemTags.MILK_DRINKS)
                .addOptionalTag(ConventionalItemTags.RAW_MEAT_FOODS)
                .addOptionalTag(ConventionalItemTags.FOOD_POISONING_FOODS)
                .addOptionalTag(ConventionalItemTags.MILK_BUCKETS);

        getOrCreateTagBuilder(TItemTags.SALTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.DIRTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.WATERY_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.WATERY_DRINKS);
    }

    private void generateCommonDrinkTags(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ThirstfulConventionalItemTags.DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.WATER_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.WATERY_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.MILK_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.HONEY_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.MAGIC_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.OMINOUS_MAGIC_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.APPLE_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.BEETROOT_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.CARROT_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.MELON_JUICE_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.WATER_DRINKS)
                .addOptional(ThirstfulDataGenerator.scorchfulId("water_skin"));

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.WATERY_DRINKS)
                .add(Items.POTION)
                .addOptionalTag(ThirstfulConventionalItemTags.WATER_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.MILK_DRINKS)
                .add(Items.MILK_BUCKET);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.HONEY_DRINKS)
                .add(Items.HONEY_BOTTLE);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.MAGIC_DRINKS)
                .add(Items.POTION)
                .addOptionalTag(ThirstfulConventionalItemTags.OMINOUS_MAGIC_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.OMINOUS_MAGIC_DRINKS)
                .add(Items.OMINOUS_BOTTLE);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.APPLE_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.BEETROOT_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.CARROT_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.MELON_JUICE_DRINKS)
                .addOptionalTag(ThirstfulConventionalItemTags.CACTUS_JUICE_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.APPLE_JUICE_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.BEETROOT_JUICE_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.CARROT_JUICE_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.MELON_JUICE_DRINKS);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.CACTUS_JUICE_DRINKS)
                .addOptional(ThirstfulDataGenerator.scorchfulId("cactus_juice"));

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.DRINK_CONTAINING_BUCKET)
                .add(Items.MILK_BUCKET);

        getOrCreateTagBuilder(ThirstfulConventionalItemTags.DRINK_CONTAINING_BOTTLE)
                .add(Items.POTION)
                .add(Items.HONEY_BOTTLE)
                .add(Items.OMINOUS_BOTTLE);
    }
}