package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.CollectWaterCallback;
import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.github.thedeathlycow.scorchful.item.WaterSkinItem;
import com.github.thedeathlycow.scorchful.registry.SDataComponentTypes;
import com.github.thedeathlycow.scorchful.registry.SItems;
import com.github.thedeathlycow.scorchful.registry.SSoundEvents;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBlock;
import com.thedeathlycow.thirstful.item.WaterCollection;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public final class ScorchfulIntegration {
    public static void initialize() {
        Thirstful.LOGGER.info("Loading Scorchful compatibility for Thirstful");
        ServerThirstPlugin.registerPlugin(new ScorchfulServerIntegration());
        CollectWaterCallback.EVENT.register((user, stack, sourcePos) -> {
            if (!user.getWorld().getBlockState(sourcePos).isOf(Blocks.WATER_CAULDRON)) {
                WaterCollection.pollutePlayerCollectedWater(stack, user, sourcePos);
            }
        });
    }

    public static void copyDrinksToOutput(SingleStackRecipeInput input, ItemStack outputStack) {
        ItemStack inputStack = input.item();
        if (inputStack.contains(SDataComponentTypes.NUM_DRINKS) && outputStack.contains(SDataComponentTypes.NUM_DRINKS)) {
            int numDrinks = inputStack.getOrDefault(SDataComponentTypes.NUM_DRINKS, 0);
            outputStack.set(SDataComponentTypes.NUM_DRINKS, numDrinks);
        }
    }

    public static void registerWaterSkinCauldronBehavior(CauldronBehavior.CauldronBehaviorMap map) {
        map.map().put(SItems.WATER_SKIN, ScorchfulIntegration::emptyIntoWaterSkin);
    }

    private static ItemActionResult emptyIntoWaterSkin(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
        if (WaterSkinItem.getNumDrinks(stack) >= WaterSkinItem.MAX_DRINKS) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!world.isClient()) {
            PollutantComponent incomingPollutants = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
            PollutantComponent existingPollutants = PollutedWaterCauldronBlock.toPollutants(state);

            state = PollutedWaterCauldronBlock.addPollutants(state, existingPollutants.mixWith(incomingPollutants));

            world.playSound(
                    null,
                    player.getBlockPos(),
                    SSoundEvents.ITEM_WATER_SKIN_FILL, SoundCategory.PLAYERS,
                    1.0f, 1.0f
            );
            world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
            player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            WaterSkinItem.addDrinks(stack, 1);

            player.incrementStat(Stats.USE_CAULDRON);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ItemActionResult.success(world.isClient);
    }

    private ScorchfulIntegration() {

    }
}