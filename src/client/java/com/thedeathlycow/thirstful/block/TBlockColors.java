package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.entity.PotionCauldronBlockEntity;
import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import com.thedeathlycow.thirstful.registry.TBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

public final class TBlockColors {
    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful block colors");
        ColorProviderRegistry.BLOCK.register(TBlockColors::getPotionCauldronColor, TBlocks.POTION_CAULDRON);
    }

    private static int getPotionCauldronColor(
            BlockState state,
            @Nullable BlockRenderView world,
            @Nullable BlockPos pos,
            int tintIndex
    ) {
        if (world != null && pos != null) {
            PotionCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POTION_CAULDRON)
                    .orElse(null);
            if (blockEntity != null) {
                return blockEntity.getPotionContents().getColor();
            }
        }

        return -1;
    }

    private TBlockColors() {

    }
}