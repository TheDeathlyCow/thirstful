package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.registry.TBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class TBlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public TBlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.CAULDRONS)
                .add(TBlocks.POLLUTED_WATER_CAULDRON);
    }
}