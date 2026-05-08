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

package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.ThirstfulClient;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.registry.TBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class TBlockColors {
    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful block colors");
        ColorProviderRegistry.BLOCK.register(TBlockColors::getPotionCauldronColor, TBlocks.POLLUTED_WATER_CAULDRON);
    }

    private static int getPotionCauldronColor(
            BlockState state,
            @Nullable BlockAndTintGetter world,
            @Nullable BlockPos pos,
            int tintIndex
    ) {
        WaterPollutionConfig pollutionConfig = Thirstful.getConfig().waterPollution();

        boolean dirty = pollutionConfig.enableDirtiness() && state.getValue(PollutedWaterCauldronBlock.DIRTY);
        boolean contaminated = pollutionConfig.enableDisease() && state.getValue(PollutedWaterCauldronBlock.CONTAMINED);

        ColorConfig colorConfig = ThirstfulClient.getConfig().color();

        if (contaminated && dirty) {
            return FastColor.ARGB32.average(colorConfig.contaminatedWaterColor(), colorConfig.dirtyWaterColor());
        } else if (contaminated) {
            return colorConfig.contaminatedWaterColor();
        } else if (dirty) {
            return colorConfig.dirtyWaterColor();
        } else if (world != null && pos != null) {
            return BiomeColors.getAverageWaterColor(world, pos);
        } else {
            return -1;
        }
    }

    private TBlockColors() {

    }
}