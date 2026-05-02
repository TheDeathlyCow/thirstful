package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import java.util.concurrent.CompletableFuture;

public class TBiomeTagGenerator extends FabricTagProvider<Biome> {
    public TBiomeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        getOrCreateTagBuilder(TBiomeTags.HAS_CLEAN_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN);

        getOrCreateTagBuilder(TBiomeTags.HAS_SAFE_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_RIVER);

        getOrCreateTagBuilder(TBiomeTags.HAS_SALTY_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN)
                .addOptionalTag(ConventionalBiomeTags.IS_BEACH)
                .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES)
                .addOptionalTag(ConventionalBiomeTags.IS_SWAMP);
    }
}