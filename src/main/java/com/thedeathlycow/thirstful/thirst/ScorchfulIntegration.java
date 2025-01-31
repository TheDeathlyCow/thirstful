package com.thedeathlycow.thirstful.thirst;

import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.Thirstful;

public final class ScorchfulIntegration {
    public static void initialize() {
        Thirstful.LOGGER.info("Loading Scorchful compatibility for Thirstful");
        ServerThirstPlugin.registerPlugin(new ScorchfulServerIntegration());
    }

    private ScorchfulIntegration() {

    }
}