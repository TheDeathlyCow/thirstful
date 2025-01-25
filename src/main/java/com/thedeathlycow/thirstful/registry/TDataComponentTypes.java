package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.ItemStackCreationCallback;
import com.thedeathlycow.thirstful.item.component.DehydratingConsumableComponent;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public final class TDataComponentTypes {
    public static final ComponentType<PollutantComponent> POLLUTANTS = register(
            "pollutants",
            builder -> builder
                    .codec(PollutantComponent.CODEC)
                    .packetCodec(PollutantComponent.PACKET_CODEC)
                    .cache()
    );

    public static final ComponentType<DehydratingConsumableComponent> DEHYDRATION_CONSUMABLE = register(
            "dehydrating_consumable",
            builder -> builder
                    .codec(DehydratingConsumableComponent.CODEC)
                    .packetCodec(DehydratingConsumableComponent.PACKET_CODEC)
                    .cache()
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful item components");

        ItemStackCreationCallback.EVENT.register(stack -> {
            if (stack.isIn(TItemTags.POLLUTED_CONSUMABLES) && !stack.contains(TDataComponentTypes.POLLUTANTS)) {
                var component = new PollutantComponent(
                        stack.isIn(TItemTags.DIRTY_BY_DEFAULT) ? 1f : 0,
                        stack.isIn(TItemTags.CONTAMINATED_BY_DEFAULT) ? 1f : 0,
                        stack.isIn(TItemTags.SALTY_BY_DEFAULT) ? 1f : 0
                );
                stack.set(TDataComponentTypes.POLLUTANTS, component);
            }
        });
    }

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builder) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Thirstful.id(id),
                builder.apply(ComponentType.builder()).build()
        );
    }

    private TDataComponentTypes() {

    }
}