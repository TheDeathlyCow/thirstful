package com.thedeathlycow.thirstful.block.entity;

import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MeatStillBlockEntity extends BlockEntity {
    public MeatStillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MeatStillBlockEntity(BlockPos pos, BlockState state) {
        this(TBlockEntityTypes.MEAT_STILL, pos, state);
    }
}
