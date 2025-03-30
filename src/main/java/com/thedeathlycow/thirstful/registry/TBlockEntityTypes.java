package com.thedeathlycow.thirstful.registry;

import com.mojang.datafixers.types.Type;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.entity.PollutedWaterCauldronBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Util;

public final class TBlockEntityTypes {
    public static final BlockEntityType<PollutedWaterCauldronBlockEntity> POLLUTED_WATER_CAULDRON = create(
            "potion_cauldron",
            BlockEntityType.Builder.create(PollutedWaterCauldronBlockEntity::new, TBlocks.POLLUTED_WATER_CAULDRON)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful block entity types");
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, builder.build(type));
    }

    private TBlockEntityTypes() {

    }
}