package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> CONVENTION_DRINKS = TagKey.of(RegistryKeys.ITEM, Thirstful.commonId("drinks"));

    public TItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(TItemTags.DRINKS)
                .add(Items.POTION)
                .add(Items.HONEY_BOTTLE)
                .add(Items.OMINOUS_BOTTLE)
                .add(Items.MILK_BUCKET)
                .addOptionalTag(CONVENTION_DRINKS);
    }
}