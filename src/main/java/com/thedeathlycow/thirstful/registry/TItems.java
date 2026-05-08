/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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