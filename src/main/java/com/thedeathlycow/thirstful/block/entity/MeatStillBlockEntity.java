package com.thedeathlycow.thirstful.block.entity;

import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class MeatStillBlockEntity extends BlockEntity {
    public MeatStillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MeatStillBlockEntity(BlockPos pos, BlockState state) {
        this(TBlockEntityTypes.MEAT_STILL, pos, state);
    }
}
