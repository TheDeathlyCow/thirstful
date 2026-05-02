package com.thedeathlycow.thirstful.registry;

import com.mojang.serialization.Codec;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import com.thedeathlycow.thirstful.item.component.DehydratingConsumableComponent;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import java.util.List;
import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

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