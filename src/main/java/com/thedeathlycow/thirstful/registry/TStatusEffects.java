package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Colors;

public final class TStatusEffects {
    public static final RegistryEntry<StatusEffect> COOLING = registerReference(
            "cooling",
            new CoolingStatusEffect(Colors.BLUE)
    );

    public static final RegistryEntry<StatusEffect> WARMING = registerReference(
        "warming",
            new WarmingStatusEffect(Colors.RED)
    );

    public static final RegistryEntry<StatusEffect> FEVER = registerReference(
            "fever",
            new FeverStatusEffect(Colors.RED)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful status effects");
        AllowStatusEffectCallback.EVENT.register(FeverStatusEffect::canHaveFever);
    }

    private static RegistryEntry<StatusEffect> registerReference(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Thirstful.id(name), statusEffect);
    }

    private TStatusEffects() {

    }
}