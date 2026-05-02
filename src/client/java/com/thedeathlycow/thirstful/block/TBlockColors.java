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