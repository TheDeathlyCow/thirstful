package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class TRegistries {
    public static final ResourceKey<Registry<ConsumePollutionEffect.Type<?>>> CONSUME_POLLUTION_EFFECT_TYPE_KEY = createRegistryKey("consume_pollution_effect");

    public static final Registry<ConsumePollutionEffect.Type<?>> CONSUME_POLLUTION_EFFECT_TYPE =
            FabricRegistryBuilder.createSimple(
                    CONSUME_POLLUTION_EFFECT_TYPE_KEY
            ).buildAndRegister();

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String registryId) {
        return ResourceKey.createRegistryKey(Thirstful.id(registryId));
    }

    private TRegistries() {

    }
}