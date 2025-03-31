package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.consume.ApplyStatusEffectConsumeEffect;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import net.minecraft.registry.Registry;

public final class TConsumePollutionEffects {
    public static final ConsumePollutionEffect.Type<ApplyStatusEffectConsumeEffect> APPLY_STATUS_EFFECT = register(
            "apply_status_effect",
            new ConsumePollutionEffect.Type<>(
                    ApplyStatusEffectConsumeEffect.CODEC,
                    ApplyStatusEffectConsumeEffect.PACKET_CODEC
            )
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful consume pollution copyEffect types");
    }

    private static <T extends ApplyStatusEffectConsumeEffect> ConsumePollutionEffect.Type<T> register(
            String name,
            ConsumePollutionEffect.Type<T> type
    ) {
        return Registry.register(TRegistries.CONSUME_POLLUTION_EFFECT_TYPE, Thirstful.id(name), type);
    }

    private TConsumePollutionEffects() {

    }
}