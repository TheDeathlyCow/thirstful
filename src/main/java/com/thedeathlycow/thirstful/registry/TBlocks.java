package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.BooleanProperty;

import java.util.function.Function;

public final class TBlocks {
    public static final Block POLLUTED_WATER_CAULDRON = register(
            "polluted_water_cauldron",
            settings -> new PollutedWaterCauldronBlock(
                    CauldronBehavior.WATER_CAULDRON_BEHAVIOR,
                    settings
            ),
            AbstractBlock.Settings.copyShallow(Blocks.CAULDRON)
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