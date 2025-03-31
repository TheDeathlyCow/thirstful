package com.thedeathlycow.thirstful.item.component;

import com.thedeathlycow.thirstful.item.consume.ApplyStatusEffectConsumeEffect;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;

public final class PollutantEffects {
    private static final int LONG_EFFECT_TIME = 60 * 20;
    private static final int SHORT_EFFECT_TIME = 10 * 20;

    public static final List<ConsumePollutionEffect> DEFAULT_DIRTINESS = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new StatusEffectInstance(StatusEffects.HUNGER, LONG_EFFECT_TIME),
                    0.5f
            )
    );

    public static final List<ConsumePollutionEffect> DEFAULT_DISEASE = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new StatusEffectInstance(StatusEffects.POISON, SHORT_EFFECT_TIME),
                    0.5f
            ),
            new ApplyStatusEffectConsumeEffect(
                    new StatusEffectInstance(TStatusEffects.FEVER, LONG_EFFECT_TIME),
                    0.5f
            )
    );

    private PollutantEffects() {

    }
}