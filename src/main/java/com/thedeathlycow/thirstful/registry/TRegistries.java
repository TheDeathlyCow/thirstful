package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public final class TRegistries {
    public static final RegistryKey<Registry<ConsumePollutionEffect.Type<?>>> CONSUME_POLLUTION_EFFECT_TYPE_KEY = createRegistryKey("consume_pollution_effect");

    public static final Registry<ConsumePollutionEffect.Type<?>> CONSUME_POLLUTION_EFFECT_TYPE =
            FabricRegistryBuilder.createSimple(
                    CONSUME_POLLUTION_EFFECT_TYPE_KEY
            ).buildAndRegister();

    private static <T> RegistryKey<Registry<T>> createRegistryKey(String registryId) {
        return RegistryKey.ofRegistry(Thirstful.id(registryId));
    }

    private TRegistries() {

    }
}