package com.thedeathlycow.thirstful.effect;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CoolingMobEffect extends TemperatureChangingMobEffect {
    public CoolingMobEffect(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.thermoo$isWarm()) {
            return super.applyEffectTick(entity, amplifier);
        }
        return false;
    }

    @Override
    protected int getTemperatureChange(int amplifier) {
        return Thirstful.getConfig().statusEffect().coolingEffectTemperatureChange() * (amplifier + 1);
    }
}