package com.thedeathlycow.thirstful.effect;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.function.IntSupplier;

public class TemperatureChangingStatusEffect extends StatusEffect {

    private final IntSupplier temperatureChangePerLevel;

    public TemperatureChangingStatusEffect(StatusEffectCategory category, int color, IntSupplier temperatureChange) {
        super(category, color);
        this.temperatureChangePerLevel = temperatureChange;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        int temperature = this.getTemperatureChange(amplifier);
        entity.thermoo$addTemperature(temperature, HeatingModes.ACTIVE);

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }

    private int getTemperatureChange(int amplifier) {
        return this.temperatureChangePerLevel.getAsInt() * amplifier;
    }
}