package com.thedeathlycow.thirstful.registry;

import com.mojang.serialization.Codec;
import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public final class TDataComponentTypes {

    public static final ComponentType<Boolean> IS_DIRTY = register("is_dirty", builder -> {
        return builder.codec(Codec.BOOL);
    });

    public static final ComponentType<Boolean> IS_CONTAMINATED = register("is_contaminated", builder -> {
        return builder.codec(Codec.BOOL);
    });

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful item components");
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