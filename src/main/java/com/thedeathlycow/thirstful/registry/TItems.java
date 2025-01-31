package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.thirst.ConsumeDirtinessAndDiseaseCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Items;

public final class TItems {
    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful items");
        ConsumeItemCallback.EVENT.register(new ConsumeDirtinessAndDiseaseCallback());

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(Items.POTION, builder -> {
                int maxStackSize = builder.getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1);

                if (maxStackSize == 1) {
                    int modSize = Thirstful.getConfig().common().thirst().potionStackSize();
                    builder.add(DataComponentTypes.MAX_STACK_SIZE, modSize);
                } else {
                    Thirstful.LOGGER.warn("Default potion max stack size was expected to be 1, but was {}", maxStackSize);
                }
            });
        });
    }

    private TItems() {

    }
}