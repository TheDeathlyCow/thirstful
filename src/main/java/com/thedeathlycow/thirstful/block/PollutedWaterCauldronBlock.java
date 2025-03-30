package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.block.entity.PollutedWaterCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class PollutedWaterCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
    public static final BooleanProperty CONTAMINED = BooleanProperty.of("contaminated");

    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PollutedWaterCauldronBlock(CauldronBehavior.CauldronBehaviorMap behaviorMap, Settings settings) {
        super(Biome.Precipitation.RAIN, behaviorMap, settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PollutedWaterCauldronBlockEntity(pos, state);
    }
}