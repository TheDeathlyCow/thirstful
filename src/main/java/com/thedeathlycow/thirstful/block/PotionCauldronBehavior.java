package com.thedeathlycow.thirstful.block;

import com.google.common.base.Suppliers;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.entity.PotionCauldronBlockEntity;
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

import java.util.function.Supplier;

public final class PotionCauldronBehavior {
    public static final CauldronBehavior.CauldronBehaviorMap BEHAVIOR_MAP = CauldronBehavior.createMap("thirstful_potion_cauldron");

    private static final Supplier<PotionContentsComponent> WATER_POTION = Suppliers.memoize(() -> new PotionContentsComponent(Potions.WATER));

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful potion cauldron behaviours");

        BEHAVIOR_MAP.map().put(Items.GLASS_BOTTLE, PotionCauldronBehavior::glassBottle);
    }

    private static ItemActionResult glassBottle(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if (!world.isClient()) {
            PotionCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POTION_CAULDRON)
                    .orElseThrow(() -> new IllegalStateException("Missing potion cauldron block entity at " + pos));

            ItemStack resultStack = Items.POTION.getDefaultStack();
            resultStack.applyComponentsFrom(blockEntity.createComponentMap());
            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, resultStack));

            player.incrementStat(Stats.USE_CAULDRON);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
        }

        return ItemActionResult.success(world.isClient);
    }

    public static void replaceWithPotionCauldron(
            ItemStack inputStack,
            BlockState afterState,
            World world,
            BlockPos pos
    ) {
        if (!afterState.isOf(Blocks.WATER_CAULDRON)) {
            return;
        }

        PollutantComponent pollution = inputStack.getOrDefault(
                TDataComponentTypes.POLLUTANTS,
                PollutantComponent.DEFAULT
        );

        PotionContentsComponent potionContents = inputStack.getOrDefault(
                DataComponentTypes.POTION_CONTENTS,
                WATER_POTION.get()
        );

        if (pollution.clean() && potionContents.matches(Potions.WATER)) {
            return;
        }

        if (!world.isClient) {
            BlockState potionCauldron = TBlocks.POTION_CAULDRON.getDefaultState()
                    .with(LeveledCauldronBlock.LEVEL, afterState.get(LeveledCauldronBlock.LEVEL));

            world.setBlockState(pos, potionCauldron);
            PotionCauldronBlockEntity blockEntity = world.getBlockEntity(pos, TBlockEntityTypes.POTION_CAULDRON).orElseThrow();
            blockEntity.setContents(potionContents, pollution);
        }
    }

    private static ItemStack getUseRemainder(ItemStack stack) {
        ItemStack recipeRemainder = stack.getRecipeRemainder();

        if (recipeRemainder.isEmpty()) {
            return Items.GLASS_BOTTLE.getDefaultStack();
        } else {
            return recipeRemainder;
        }
    }

    private PotionCauldronBehavior() {

    }
}