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

import com.mojang.serialization.Codec;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ConsumeItemCallback;
import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import com.thedeathlycow.thirstful.item.component.DehydratingConsumableComponent;
import com.thedeathlycow.thirstful.item.component.DrinkComponent;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.UnaryOperator;

public final class TDataComponentTypes {
    public static final DataComponentType<PollutantComponent> POLLUTANTS = register(
            "pollutants",
            builder -> builder
                    .persistent(PollutantComponent.CODEC)
                    .networkSynchronized(PollutantComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<DehydratingConsumableComponent> DEHYDRATION_CONSUMABLE = register(
            "dehydrating_consumable",
            builder -> builder
                    .persistent(DehydratingConsumableComponent.CODEC)
                    .networkSynchronized(DehydratingConsumableComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<List<ConsumePollutionEffect>> DIRTINESS_EFFECTS = register(
            "dirtiness_effects",
            builder -> builder
                    .persistent(Codec.list(ConsumePollutionEffect.ELEMENT_CODEC))
                    .networkSynchronized(ConsumePollutionEffect.PACKET_CODEC.apply(ByteBufCodecs.list()))
                    .cacheEncoding()
    );

    public static final DataComponentType<List<ConsumePollutionEffect>> DISEASE_EFFECTS = register(
            "disease_effects",
            builder -> builder
                    .persistent(Codec.list(ConsumePollutionEffect.ELEMENT_CODEC))
                    .networkSynchronized(ConsumePollutionEffect.PACKET_CODEC.apply(ByteBufCodecs.list()))
                    .cacheEncoding()
    );

    public static final DataComponentType<List<ConsumePollutionEffect>> SALTINESS_EFFECTS = register(
            "saltiness_effects",
            builder -> builder
                    .persistent(Codec.list(ConsumePollutionEffect.ELEMENT_CODEC))
                    .networkSynchronized(ConsumePollutionEffect.PACKET_CODEC.apply(ByteBufCodecs.list()))
                    .cacheEncoding()
    );

    public static final DataComponentType<DrinkComponent> DRINK = register(
            "drink",
            builder -> builder
                    .persistent(DrinkComponent.CODEC)
                    .networkSynchronized(DrinkComponent.STREAM_CODEC)
                    .cacheEncoding()
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful item components");

        ItemStackCreationCallback.EVENT.register(stack -> {
            if (WaterPollution.canCarryPollutants(stack) && !stack.has(TDataComponentTypes.POLLUTANTS)) {
                var component = new PollutantComponent(
                        stack.is(TItemTags.DIRTY_BY_DEFAULT),
                        stack.is(TItemTags.CONTAMINATED_BY_DEFAULT),
                        stack.is(TItemTags.SALTY_BY_DEFAULT)
                );
                stack.set(TDataComponentTypes.POLLUTANTS, component);
            }
        });

        ConsumeItemCallback.EVENT.register((entity, stack) -> {
            if (entity instanceof Player player) {
                DrinkComponent.getByTag(stack).drink(player);
            }
        });
    }

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Thirstful.id(id),
                builder.apply(DataComponentType.builder()).build()
        );
    }

    private TDataComponentTypes() {

    }
}