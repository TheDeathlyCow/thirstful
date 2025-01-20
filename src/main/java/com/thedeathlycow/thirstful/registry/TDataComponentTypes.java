package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.StackCreationCallback;
import com.thedeathlycow.thirstful.item.component.DrinkPurityComponent;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public final class TDataComponentTypes {
    public static final ComponentType<DrinkPurityComponent> DRINK_PURITY = register(
            "drink_purity",
            builder -> builder
                    .codec(DrinkPurityComponent.CODEC)
                    .packetCodec(DrinkPurityComponent.PACKET_CODEC)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful item components");

        StackCreationCallback.EVENT.register(stack -> {
            if (stack.isIn(TItemTags.DRINKS) && !stack.contains(TDataComponentTypes.DRINK_PURITY)) {
                stack.set(TDataComponentTypes.DRINK_PURITY, new DrinkPurityComponent());
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