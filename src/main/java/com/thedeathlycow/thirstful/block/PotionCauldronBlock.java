package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.block.entity.PotionCauldronBlockEntity;
import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class PotionCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PotionCauldronBlock(CauldronBehavior.CauldronBehaviorMap behaviorMap, Settings settings) {
        super(Biome.Precipitation.RAIN, behaviorMap, settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PotionCauldronBlockEntity(pos, state);
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> validateTicker(
            BlockEntityType<A> givenType,
            BlockEntityType<E> expectedType,
            BlockEntityTicker<? super E> ticker
    ) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}