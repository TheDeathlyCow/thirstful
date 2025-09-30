package com.thedeathlycow.thirstful.block;

import com.mojang.serialization.MapCodec;
import com.thedeathlycow.thirstful.block.entity.MeatStillBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MeatStillBlock extends BlockWithEntity {
    public MeatStillBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    @Nullable
    public MeatStillBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MeatStillBlockEntity(pos, state);
    }
}
