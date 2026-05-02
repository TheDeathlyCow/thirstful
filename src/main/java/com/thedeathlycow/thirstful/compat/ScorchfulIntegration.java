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
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public final class ScorchfulIntegration {
    public static void initialize() {
        Thirstful.LOGGER.info("Loading Scorchful compatibility for Thirstful");
        ServerThirstPlugin.registerPlugin(new ThirstfulServerThirstPlugin());
        CollectWaterCallback.EVENT.register((user, stack, sourcePos) -> {
            if (!user.level().getBlockState(sourcePos).is(Blocks.WATER_CAULDRON)) {
                WaterCollection.pollutePlayerCollectedWater(stack, user, sourcePos);
            }
        });
    }

    public static void copyDrinksToOutput(SingleRecipeInput input, ItemStack outputStack) {
        ItemStack inputStack = input.item();
        if (inputStack.has(SDataComponentTypes.NUM_DRINKS) && outputStack.has(SDataComponentTypes.NUM_DRINKS)) {
            int numDrinks = inputStack.getOrDefault(SDataComponentTypes.NUM_DRINKS, 0);
            outputStack.set(SDataComponentTypes.NUM_DRINKS, numDrinks);
        }
    }

    public static void registerWaterSkinCauldronBehavior(CauldronInteraction.InteractionMap map) {
        map.map().put(SItems.WATER_SKIN, ScorchfulIntegration::emptyIntoWaterSkin);
    }

    private static ItemInteractionResult emptyIntoWaterSkin(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) {
        if (WaterSkinItem.getNumDrinks(stack) >= WaterSkinItem.MAX_DRINKS) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!world.isClientSide()) {
            PollutantComponent incomingPollutants = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);
            PollutantComponent existingPollutants = PollutedWaterCauldronBlock.toPollutants(state);

            state = PollutedWaterCauldronBlock.addPollutants(state, existingPollutants.mixWith(incomingPollutants));

            world.playSound(
                    null,
                    player.blockPosition(),
                    SSoundEvents.ITEM_WATER_SKIN_FILL, SoundSource.PLAYERS,
                    1.0f, 1.0f
            );
            world.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            WaterSkinItem.addDrinks(stack, 1);

            player.awardStat(Stats.USE_CAULDRON);
            LayeredCauldronBlock.lowerFillLevel(state, world, pos);
        }
        return ItemInteractionResult.sidedSuccess(world.isClientSide);
    }

    private ScorchfulIntegration() {

    }
}