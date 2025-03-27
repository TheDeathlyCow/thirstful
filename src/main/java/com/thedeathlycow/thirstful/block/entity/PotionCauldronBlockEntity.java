package com.thedeathlycow.thirstful.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class PotionCauldronBlockEntity extends BlockEntity {
    public PotionCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}