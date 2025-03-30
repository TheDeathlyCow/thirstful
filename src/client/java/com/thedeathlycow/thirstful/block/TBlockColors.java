package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import com.thedeathlycow.thirstful.registry.TBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.Colors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public final class TBlockColors {
    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful block colors");
        ColorProviderRegistry.BLOCK.register(TBlockColors::getPotionCauldronColor, TBlocks.POLLUTED_WATER_CAULDRON);
    }

    private static int getPotionCauldronColor(
            BlockState state,
            @Nullable BlockRenderView world,
            @Nullable BlockPos pos,
            int tintIndex
    ) {
        boolean contaminated = state.get(PollutedWaterCauldronBlock.CONTAMINED);
        boolean dirty = state.get(PollutedWaterCauldronBlock.DIRTY);

        ColorConfig config = Thirstful.getConfig().client().color();

        if (contaminated && dirty) {
            return ColorHelper.Argb.mixColor(config.contaminatedWaterColor(), config.dirtyWaterColor());
        } else if (contaminated) {
            return config.contaminatedWaterColor();
        } else if (dirty) {
            return config.dirtyWaterColor();
        } else if (world != null && pos != null) {
            return BiomeColors.getWaterColor(world, pos);
        } else {
            return -1;
        }
    }

    private TBlockColors() {

    }
}