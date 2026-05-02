package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class PollutedWaterCauldronBlock extends LayeredCauldronBlock {
    public static final BooleanProperty CONTAMINED = BooleanProperty.create("contaminated");
    public static final BooleanProperty DIRTY = BooleanProperty.create("dirty");
    public static final BooleanProperty SALTY = BooleanProperty.create("salty");

    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PollutedWaterCauldronBlock(CauldronInteraction.InteractionMap behaviorMap, Properties settings) {
        super(Biome.Precipitation.RAIN, behaviorMap, settings);
        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(CONTAMINED, false)
                        .setValue(DIRTY, false)
                        .setValue(SALTY, false)
        );
    }

    public static PollutantComponent toPollutants(BlockState state) {
        return new PollutantComponent(
                state.getValue(DIRTY),
                state.getValue(CONTAMINED),
                state.getValue(SALTY)
        );
    }

    public static BlockState addPollutants(BlockState state, PollutantComponent pollutants) {
        return state
                .setValue(DIRTY, pollutants.dirty())
                .setValue(CONTAMINED, pollutants.contaminated())
                .setValue(SALTY, pollutants.salty());
    }

    public static BlockState fillWithRain(BlockState state) {
        return TBlocks.POLLUTED_WATER_CAULDRON.withPropertiesOf(state)
                .setValue(CONTAMINED, true);
    }

    public static BlockState fillFromDripstone(BlockState state) {
        return TBlocks.POLLUTED_WATER_CAULDRON.withPropertiesOf(state)
                .setValue(DIRTY, true)
                .setValue(CONTAMINED, true);
    }

    @Override
    public Item asItem() {
        return Items.CAULDRON;
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level world,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        CauldronInteraction cauldronBehavior = this.interactions.map().get(stack.getItem());
        return cauldronBehavior.interact(state, world, pos, player, hand, stack);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CONTAMINED, DIRTY, SALTY);
    }
}