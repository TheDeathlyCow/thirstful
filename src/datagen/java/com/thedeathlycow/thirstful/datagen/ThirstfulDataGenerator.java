package com.thedeathlycow.thirstful.datagen;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.datagen.generator.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ThirstfulDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        TBlockTagGenerator blockTags = pack.addProvider(TBlockTagGenerator::new);
        pack.addProvider((output, wrapperLookup) -> new TItemTagGenerator(output, wrapperLookup, blockTags));
        pack.addProvider(TBiomeTagGenerator::new);
        pack.addProvider(EnglishUSGenerator::new);
        pack.addProvider(RecipeGenerator::new);
    }

    public static ResourceLocation commonId(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    public static ResourceLocation scorchfulId(String path) {
        return ResourceLocation.fromNamespaceAndPath("scorchful", path);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return Thirstful.MODID;
    }
}
