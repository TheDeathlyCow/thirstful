package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.component.PollutantEffects;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public final class TItems {
    public static final Item MEAT_STILL = register("meat_still", TBlocks.MEAT_STILL);

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful items");
        ConsumeItemCallback.EVENT.register(PollutantEffects::onConsume);

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(Items.POTION, builder -> {
                int maxStackSize = builder.getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1);

                if (maxStackSize == 1) {
                    int modSize = Thirstful.getConfig().thirst().potionStackSize();
                    builder.add(DataComponentTypes.MAX_STACK_SIZE, modSize);
                } else {
                    Thirstful.LOGGER.warn("Thirstful expected the default potion max stack size to be 1, but was {}.", maxStackSize);
                }
            });
        });
    }

    private static Item register(String id, Block block) {
        return register(id, settings -> new BlockItem(block, settings));
    }

    private static Item register(String id) {
        return register(id, Item::new, new Item.Settings());
    }

    private static Item register(String id, Function<Item.Settings, Item> itemFactory) {
        return register(id, itemFactory, new Item.Settings());
    }

    private static Item register(String id, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        Item item = itemFactory.apply(settings);
        return Registry.register(Registries.ITEM, Thirstful.id(id), item);
    }

    private TItems() {

    }
}