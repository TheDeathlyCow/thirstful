package com.thedeathlycow.thirstful.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.block.entity.MeatStillBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MeatStillBlock extends BlockWithEntity {
    public static final MapCodec<MeatStillBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance ->
                    instance.group(
                            createSettingsCodec()
                    ).apply(instance, MeatStillBlock::new)
    );

    public MeatStillBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends MeatStillBlock> getCodec() {
        return CODEC;
    }

    @Override
    @Nullable
    public MeatStillBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MeatStillBlockEntity(pos, state);
    }
}
