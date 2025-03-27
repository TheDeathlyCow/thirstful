package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.PotionCauldronBehavior;
import com.thedeathlycow.thirstful.block.PotionCauldronBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public final class TBlocks {
    public static final Block POTION_CAULDRON = register(
            "potion_cauldron",
            settings -> new PotionCauldronBlock(PotionCauldronBehavior.BEHAVIOR_MAP, settings)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful blocks");
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory) {
        return register(name, blockFactory, AbstractBlock.Settings.create());
    }

    private static Block register(
            String name,
            Function<AbstractBlock.Settings, Block> blockFactory,
            AbstractBlock.Settings settings
    ) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Thirstful.id(name));
        Block block = blockFactory.apply(settings);
        return Registry.register(Registries.BLOCK, key, block);
    }

    private TBlocks() {

    }
}