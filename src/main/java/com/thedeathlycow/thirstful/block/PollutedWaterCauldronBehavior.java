package com.thedeathlycow.thirstful.block;

import com.google.common.base.Suppliers;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.entity.PollutedWaterCauldronBlockEntity;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlockEntityTypes;
import com.thedeathlycow.thirstful.registry.TBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public final class PollutedWaterCauldronBehavior {
    public static final CauldronBehavior.CauldronBehaviorMap BEHAVIOR_MAP = CauldronBehavior.createMap("thirstful_potion_cauldron");

    public static final Supplier<PotionContentsComponent> WATER_POTION = Suppliers.memoize(() -> new PotionContentsComponent(Potions.WATER));

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful potion cauldron behaviours");
//        BEHAVIOR_MAP.map().put(Items.GLASS_BOTTLE, PollutedWaterCauldronBehavior::glassBottle);
//        BEHAVIOR_MAP.map().put(Items.POTION, PollutedWaterCauldronBehavior::potion);
    }

    public static void replaceWithPollutedWaterCauldron(
            PollutantComponent inputPollution,
            BlockState state,
            World world,
            BlockPos pos
    ) {
        if (state.isOf(Blocks.WATER_CAULDRON) && !world.isClient()) {
            BlockState pollutedCauldron = TBlocks.POLLUTED_WATER_CAULDRON.getDefaultState()
                    .with(LeveledCauldronBlock.LEVEL, state.get(LeveledCauldronBlock.LEVEL))
                    .with(PollutedWaterCauldronBlock.CONTAMINED, inputPollution.contaminated());

            world.setBlockState(pos, pollutedCauldron);

            PollutedWaterCauldronBlockEntity blockEntity = world.getBlockEntity(
                    pos,
                    TBlockEntityTypes.POLLUTED_WATER_CAULDRON
            ).orElseThrow();

            blockEntity.setContents(inputPollution);
        }
    }

//    private static ItemActionResult glassBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
//        if (!world.isClient()) {
//            PotionCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POLLUTED_WATER_CAULDRON)
//                    .orElseThrow(() -> new IllegalStateException("Missing potion cauldron block entity at " + pos));
//
//            ItemStack resultStack = Items.POTION.getDefaultStack();
//            resultStack.applyComponentsFrom(blockEntity.createComponentMap());
//            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, resultStack));
//
//            player.incrementStat(Stats.USE_CAULDRON);
//            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
//
//            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
//
//            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
//            world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
//        }
//
//        return ItemActionResult.success(world.isClient());
//    }
//
//    private static ItemActionResult potion(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
//        if (state.get(LeveledCauldronBlock.LEVEL) == LeveledCauldronBlock.MAX_LEVEL) {
//            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//        }
//
//        PotionContentsComponent potionContents = stack.get(DataComponentTypes.POTION_CONTENTS);
//        PotionCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POLLUTED_WATER_CAULDRON).orElseThrow();
//
//        if (potionContents != null && potionContents.equals(blockEntity.getPotionContents())) {
//
//            if (!world.isClient()) {
//                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
//
//                player.incrementStat(Stats.USE_CAULDRON);
//                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
//
//                world.setBlockState(pos, state.cycle(LeveledCauldronBlock.LEVEL));
//
//                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
//                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
//            }
//
//            return ItemActionResult.success(world.isClient());
//        } else {
//            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
//        }
//    }

    private PollutedWaterCauldronBehavior() {

    }
}