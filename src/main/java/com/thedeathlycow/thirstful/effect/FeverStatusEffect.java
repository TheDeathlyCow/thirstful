package com.thedeathlycow.thirstful.effect;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FeverStatusEffect extends TemperatureChangingStatusEffect {
    public FeverStatusEffect(int color) {
        super(StatusEffectCategory.HARMFUL, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        float targetScale = Thirstful.getConfig().common().statusEffect().feverMinTemperatureScale();
        if (entity.thermoo$getTemperatureScale() <= targetScale) {
            super.applyUpdateEffect(entity, amplifier);
        }
        return true;
    }

    @Override
    protected int getTemperatureChange(int amplifier) {
        return Thirstful.getConfig().common().statusEffect().feverEffectTemperatureChange() * (amplifier + 1);
    }
}