package com.thedeathlycow.thirstful.effect;

import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public abstract class TemperatureChangingMobEffect extends ThirstfulMobEffect {

    public TemperatureChangingMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        int temperature = this.getTemperatureChange(amplifier);
        entity.thermoo$addTemperature(temperature, HeatingModes.ACTIVE);

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    protected abstract int getTemperatureChange(int amplifier);
}