package com.thedeathlycow.thirstful.registry;

import com.github.thedeathlycow.scorchful.event.ScorchfulItemEvents;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.thirst.PlayerConsumptionListener;

public final class TItems {
    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful items");
        ScorchfulItemEvents.CONSUME_ITEM.register(new PlayerConsumptionListener());
    }

    private TItems() {

    }
}