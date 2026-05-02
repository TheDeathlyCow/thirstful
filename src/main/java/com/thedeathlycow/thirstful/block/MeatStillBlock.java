package com.thedeathlycow.thirstful.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.block.entity.MeatStillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MeatStillBlock extends BaseEntityBlock {
    public static final MapCodec<MeatStillBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance ->
                    instance.group(
                            propertiesCodec()
                    ).apply(instance, MeatStillBlock::new)
    );

    public MeatStillBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends MeatStillBlock> codec() {
        return CODEC;
    }

    @Override
    @Nullable
    public MeatStillBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MeatStillBlockEntity(pos, state);
    }
}
