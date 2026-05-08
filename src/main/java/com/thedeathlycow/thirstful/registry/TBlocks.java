/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.MeatStillBlock;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBehavior;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class TBlocks {
    public static final Block POLLUTED_WATER_CAULDRON = register(
            "polluted_water_cauldron",
            settings -> new PollutedWaterCauldronBlock(
                    PollutedWaterCauldronBehavior.BEHAVIOR_MAP,
                    settings
            ),
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.CAULDRON)
    );

    public static final Block MEAT_STILL = register(
            "meat_still",
            MeatStillBlock::new
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful blocks");
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory) {
        return register(name, blockFactory, BlockBehaviour.Properties.of());
    }

    private static Block register(
            String name,
            Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties settings
    ) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Thirstful.id(name));
        Block block = blockFactory.apply(settings);
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    private TBlocks() {

    }
}