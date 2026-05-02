package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.component.PollutantEffects;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import java.util.function.Function;

public final class TItems {
    public static final Item MEAT_STILL = register("meat_still", TBlocks.MEAT_STILL);

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful items");
        ConsumeItemCallback.EVENT.register(PollutantEffects::onConsume);

        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(Items.POTION, builder -> {
                int maxStackSize = builder.getOrDefault(DataComponents.MAX_STACK_SIZE, 1);

                if (maxStackSize == 1) {
                    int modSize = Thirstful.getConfig().thirst().potionStackSize();
                    builder.set(DataComponents.MAX_STACK_SIZE, modSize);
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
        return register(id, Item::new, new Item.Properties());
    }

    private static Item register(String id, Function<Item.Properties, Item> itemFactory) {
        return register(id, itemFactory, new Item.Properties());
    }

    private static Item register(String id, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        Item item = itemFactory.apply(settings);
        return Registry.register(BuiltInRegistries.ITEM, Thirstful.id(id), item);
    }

    private TItems() {

    }
}