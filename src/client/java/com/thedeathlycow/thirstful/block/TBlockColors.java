package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public final class TBlockColors {
    private static final int CONTAMINED_COLOR = 0x008000;

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
        if (Boolean.TRUE.equals(state.get(PollutedWaterCauldronBlock.CONTAMINED))) {
            return CONTAMINED_COLOR;
        } else if (world != null && pos != null) {
            return BiomeColors.getWaterColor(world, pos);
        } else {
            return -1;
        }
    }

    private TBlockColors() {

    }
}