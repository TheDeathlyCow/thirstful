package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class TBiomeTagGenerator extends FabricTagProvider<Biome> {
    public TBiomeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(TBiomeTags.HAS_CLEAN_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN);

        getOrCreateTagBuilder(TBiomeTags.HAS_SAFE_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_RIVER);

        getOrCreateTagBuilder(TBiomeTags.HAS_SALTY_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN)
                .addOptionalTag(ConventionalBiomeTags.IS_BEACH)
                .addOptionalTag(ConventionalBiomeTags.IS_SWAMP);
    }
}