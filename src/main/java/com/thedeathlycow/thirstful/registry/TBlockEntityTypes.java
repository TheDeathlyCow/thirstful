package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.entity.PollutedWaterCauldronBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class TBlockEntityTypes {
    public static final BlockEntityType<PollutedWaterCauldronBlockEntity> POLLUTED_WATER_CAULDRON = create(
            "polluted_water_cauldron",
            BlockEntityType.Builder.create(PollutedWaterCauldronBlockEntity::new, TBlocks.POLLUTED_WATER_CAULDRON)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful block entity types");
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, builder.build());
    }

    private TBlockEntityTypes() {

    }
}