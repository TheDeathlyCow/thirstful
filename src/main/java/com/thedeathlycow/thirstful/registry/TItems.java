package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.thirst.ConsumeDirtinessAndDiseaseCallback;

public final class TItems {
    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful items");
        ConsumeItemCallback.EVENT.register(new ConsumeDirtinessAndDiseaseCallback());
    }

    private TItems() {

    }
}