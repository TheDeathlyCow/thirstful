package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.entity.PollutedWaterCauldronBlockEntity;
import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import com.thedeathlycow.thirstful.registry.TBlocks;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public final class PollutedWaterCauldronBehavior {
    public static final CauldronBehavior.CauldronBehaviorMap BEHAVIOR_MAP = CauldronBehavior.createMap("thirstful_potion_cauldron");

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
            World world,
            BlockPos pos
    ) {
        if (state.isOf(Blocks.WATER_CAULDRON) && !world.isClient()) {
            BlockState pollutedCauldron = TBlocks.POLLUTED_WATER_CAULDRON.getDefaultState()
                    .with(LeveledCauldronBlock.LEVEL, state.get(LeveledCauldronBlock.LEVEL));

            world.setBlockState(pos, pollutedCauldron);

            PollutedWaterCauldronBlockEntity blockEntity = world.getBlockEntity(
                    pos,
                    TBlockEntityTypes.POLLUTED_WATER_CAULDRON
            ).orElseThrow();

            blockEntity.setContents(inputPollution);
        }
    }

    private static ItemActionResult emptyIntoGlassBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if (!world.isClient()) {
            PollutedWaterCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POLLUTED_WATER_CAULDRON)
                    .orElseThrow(() -> new IllegalStateException("Missing potion cauldron block entity at " + pos));

            ItemStack resultStack = PotionContentsComponent.createStack(Items.POTION, Potions.WATER);
            resultStack.set(TDataComponentTypes.POLLUTANTS, blockEntity.getPollutants());

            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, resultStack));

            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
        }

        return ItemActionResult.success(world.isClient());
    }

    private static ItemActionResult emptyIntoBucket(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        PollutedWaterCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POLLUTED_WATER_CAULDRON)
                .orElseThrow(() -> new IllegalStateException("Missing potion cauldron block entity at " + pos));

        ItemStack output = Items.WATER_BUCKET.getDefaultStack();
        output.set(TDataComponentTypes.POLLUTANTS, blockEntity.getPollutants());

        return CauldronBehavior.emptyCauldron(
                state,
                world,
                pos,
                player,
                hand,
                stack,
                output,
                s -> s.get(LeveledCauldronBlock.LEVEL) == LeveledCauldronBlock.MAX_LEVEL,
                SoundEvents.ITEM_BUCKET_FILL
        );
    }

    private static ItemActionResult fillFromPotion(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if (state.get(LeveledCauldronBlock.LEVEL) == LeveledCauldronBlock.MAX_LEVEL) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        PotionContentsComponent potionContents = stack.get(DataComponentTypes.POTION_CONTENTS);

        if (potionContents != null && potionContents.matches(Potions.WATER)) {
            if (!world.isClient()) {
                PollutantComponent pollutants = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
                PollutedWaterCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POLLUTED_WATER_CAULDRON).orElseThrow();
                blockEntity.setContents(pollutants);

                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));

                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL));

                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
            }

            return ItemActionResult.success(world.isClient());
        } else {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    private PollutedWaterCauldronBehavior() {

    }
}