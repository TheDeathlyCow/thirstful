package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.CollectWaterCallback;
import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.github.thedeathlycow.scorchful.registry.SDataComponentTypes;
import com.github.thedeathlycow.scorchful.registry.SItems;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.WaterCollection;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.SingleStackRecipeInput;

public final class ScorchfulIntegration {
    public static void initialize() {
        Thirstful.LOGGER.info("Loading Scorchful compatibility for Thirstful");
        ServerThirstPlugin.registerPlugin(new ScorchfulServerIntegration());
        CollectWaterCallback.EVENT.register((user, stack, sourcePos) -> {
            WaterCollection.pollutePlayerCollectedWater(stack, user, sourcePos);
        });
    }

    public static void copyDrinksToOutput(SingleStackRecipeInput input, ItemStack outputStack) {
        ItemStack inputStack = input.item();
        if (inputStack.contains(SDataComponentTypes.NUM_DRINKS) && outputStack.contains(SDataComponentTypes.NUM_DRINKS)) {
            int numDrinks = inputStack.getOrDefault(SDataComponentTypes.NUM_DRINKS, 0);
            outputStack.set(SDataComponentTypes.NUM_DRINKS, numDrinks);
        }
    }

    private ScorchfulIntegration() {

    }
}