package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlocks;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public final class PollutedWaterCauldronBehavior {
    public static final CauldronInteraction.InteractionMap BEHAVIOR_MAP = CauldronInteraction.newInteractionMap("thirstful_potion_cauldron");

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful potion cauldron behaviours");
        BEHAVIOR_MAP.map().put(Items.GLASS_BOTTLE, PollutedWaterCauldronBehavior::emptyIntoGlassBottle);
        BEHAVIOR_MAP.map().put(Items.BUCKET, PollutedWaterCauldronBehavior::emptyIntoBucket);
        BEHAVIOR_MAP.map().put(Items.POTION, PollutedWaterCauldronBehavior::fillFromPotion);

        if (ModIntegration.isScorchfulLoaded()) {
            ScorchfulIntegration.registerWaterSkinCauldronBehavior(BEHAVIOR_MAP);
        }
    }

    public static void replaceWithPollutedWaterCauldron(
            PollutantComponent inputPollution,
            BlockState state,
            Level world,
            BlockPos pos
    ) {
        if (state.is(Blocks.WATER_CAULDRON) && !world.isClientSide()) {
            BlockState pollutedCauldron = PollutedWaterCauldronBlock.addPollutants(
                    TBlocks.POLLUTED_WATER_CAULDRON.defaultBlockState(),
                    inputPollution
            ).setValue(LayeredCauldronBlock.LEVEL, state.getValue(LayeredCauldronBlock.LEVEL));

            world.setBlockAndUpdate(pos, pollutedCauldron);
        }
    }

    private static ItemInteractionResult emptyIntoGlassBottle(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (!world.isClientSide()) {
            ItemStack resultStack = PotionContents.createItemStack(Items.POTION, Potions.WATER);
            resultStack.set(TDataComponentTypes.POLLUTANTS, PollutedWaterCauldronBlock.toPollutants(state));

            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, resultStack));

            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

            LayeredCauldronBlock.lowerFillLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            world.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
        }

        return ItemInteractionResult.sidedSuccess(world.isClientSide());
    }

    private static ItemInteractionResult emptyIntoBucket(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        ItemStack output = Items.WATER_BUCKET.getDefaultInstance();
        output.set(TDataComponentTypes.POLLUTANTS, PollutedWaterCauldronBlock.toPollutants(state));

        return CauldronInteraction.fillBucket(
                state,
                world,
                pos,
                player,
                hand,
                stack,
                output,
                s -> s.getValue(LayeredCauldronBlock.LEVEL) == LayeredCauldronBlock.MAX_FILL_LEVEL,
                SoundEvents.BUCKET_FILL
        );
    }

    private static ItemInteractionResult fillFromPotion(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (state.getValue(LayeredCauldronBlock.LEVEL) == LayeredCauldronBlock.MAX_FILL_LEVEL) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        PotionContents potionContents = stack.get(DataComponents.POTION_CONTENTS);

        if (potionContents != null && potionContents.is(Potions.WATER)) {
            if (!world.isClientSide()) {
                PollutantComponent incomingPollutants = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
                PollutantComponent existingPollutants = PollutedWaterCauldronBlock.toPollutants(state);

                state = PollutedWaterCauldronBlock.addPollutants(state, existingPollutants.mixWith(incomingPollutants));

                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));

                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                world.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));

                world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            }

            return ItemInteractionResult.sidedSuccess(world.isClientSide());
        } else {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    private PollutedWaterCauldronBehavior() {

    }
}