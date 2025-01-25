package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.effect.TemperatureChangingStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;

public final class TStatusEffects {
    public static final RegistryEntry<StatusEffect> COOLING = registerReference(
            "cooling",
            new TemperatureChangingStatusEffect(
                    StatusEffectCategory.NEUTRAL,
                    Colors.BLUE,
                    () -> Thirstful.getConfig().common.statusEffect.getCoolingEffectTemperatureChange()
            )
    );

    public static final RegistryEntry<StatusEffect> WARMING = registerReference(
        "warming",
            new TemperatureChangingStatusEffect(
                    StatusEffectCategory.NEUTRAL,
                    Colors.RED,
                    () -> Thirstful.getConfig().common.statusEffect.getWarmingEffectTemperatureChange()
            )
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful status effects");
    }

    private static RegistryEntry<StatusEffect> registerReference(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Thirstful.id(name), statusEffect);
    }

    private TStatusEffects() {

    }
}