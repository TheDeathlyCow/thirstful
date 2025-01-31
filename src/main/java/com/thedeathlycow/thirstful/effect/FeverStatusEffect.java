package com.thedeathlycow.thirstful.effect;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class FeverStatusEffect extends TemperatureChangingStatusEffect {
    public FeverStatusEffect(int color) {
        super(StatusEffectCategory.HARMFUL, color);
    }

    public static TriState canHaveFever(LivingEntity entity, StatusEffectInstance effectInstance) {
        if (effectInstance.equals(TStatusEffects.FEVER)) {
            // in practice, this will allow fever whenever scorchful is loaded
            return TriState.of(entity.thermoo$getMaxTemperature() > 0);
        } else {
            return TriState.DEFAULT;
        }
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