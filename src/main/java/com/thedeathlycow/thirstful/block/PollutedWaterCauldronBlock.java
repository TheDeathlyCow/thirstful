package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.block.entity.PollutedWaterCauldronBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class PollutedWaterCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
    public static final BooleanProperty CONTAMINED = BooleanProperty.of("contaminated");
    public static final BooleanProperty DIRTY = BooleanProperty.of("dirty");

    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PollutedWaterCauldronBlock(CauldronBehavior.CauldronBehaviorMap behaviorMap, Settings settings) {
        super(Biome.Precipitation.RAIN, behaviorMap, settings);
        this.setDefaultState(
                this.getDefaultState()
                        .with(CONTAMINED, false)
                        .with(DIRTY, false)
        );
    }

    @Override
    public Item asItem() {
        return Items.CAULDRON;
    }

    @Override
    protected ItemActionResult onUseWithItem(
            ItemStack stack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {
        CauldronBehavior cauldronBehavior = this.behaviorMap.map().get(stack.getItem());
        return cauldronBehavior.interact(state, world, pos, player, hand, stack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONTAMINED, DIRTY);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PollutedWaterCauldronBlockEntity(pos, state);
    }
}