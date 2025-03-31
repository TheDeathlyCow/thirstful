package com.thedeathlycow.thirstful.effect;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class TemperatureChangingStatusEffect extends StatusEffect {

    public TemperatureChangingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        int temperature = this.getTemperatureChange(amplifier);
        entity.thermoo$addTemperature(temperature, HeatingModes.ACTIVE);

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    protected abstract int getTemperatureChange(int amplifier);
}