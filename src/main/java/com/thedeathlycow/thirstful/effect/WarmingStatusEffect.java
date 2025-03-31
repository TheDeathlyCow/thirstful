package com.thedeathlycow.thirstful.effect;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;

public class WarmingStatusEffect extends TemperatureChangingStatusEffect {
    public WarmingStatusEffect(int color) {
        super(StatusEffectCategory.BENEFICIAL, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.thermoo$isCold()) {
            return super.applyUpdateEffect(entity, amplifier);
        }
        return false;
    }

    @Override
    protected int getTemperatureChange(int amplifier) {
        return Thirstful.getConfig().statusEffect().warmingEffectTemperatureChange() * (amplifier + 1);
    }
}