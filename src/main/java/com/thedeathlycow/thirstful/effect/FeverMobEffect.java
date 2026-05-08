/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.thedeathlycow.thirstful.effect;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.registry.TMobEffects;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class FeverMobEffect extends TemperatureChangingMobEffect {
    public FeverMobEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);
    }

    public static TriState canHaveFever(LivingEntity entity, MobEffectInstance effectInstance) {
        if (effectInstance.is(TMobEffects.FEVER)) {
            // in practice, this will allow fever whenever scorchful is loaded
            return TriState.of(entity.thermoo$getMaxTemperature() > 0);
        } else {
            return TriState.DEFAULT;
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        float targetScale = Thirstful.getConfig().statusEffect().feverMinTemperatureScale();
        if (entity.thermoo$getTemperatureScale() <= targetScale) {
            super.applyEffectTick(entity, amplifier);
        }

        return true;
    }

    @Override
    protected int getTemperatureChange(int amplifier) {
        return Thirstful.getConfig().statusEffect().feverEffectTemperatureChange() * (amplifier + 1);
    }


}