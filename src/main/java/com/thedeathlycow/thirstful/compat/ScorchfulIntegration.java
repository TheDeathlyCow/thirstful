package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.CollectWaterCallback;
import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.WaterCollection;

public final class ScorchfulIntegration {
    public static void initialize() {
        Thirstful.LOGGER.info("Loading Scorchful compatibility for Thirstful");
        ServerThirstPlugin.registerPlugin(new ScorchfulServerIntegration());
        CollectWaterCallback.EVENT.register((user, stack, sourcePos) -> {
            WaterCollection.pollutePlayerCollectedWater(stack, user, sourcePos);
        });
    }

    private ScorchfulIntegration() {

    }
}