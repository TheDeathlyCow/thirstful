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

package com.thedeathlycow.thirstful.item.component;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.consume.ApplyStatusEffectConsumeEffect;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.TMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

public final class PollutantEffects {
    private static final int LONG_EFFECT_TIME = 60 * 20;
    private static final int SHORT_EFFECT_TIME = 10 * 20;

    public static final List<ConsumePollutionEffect> DEFAULT_DIRTINESS = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new MobEffectInstance(MobEffects.HUNGER, LONG_EFFECT_TIME),
                    0.5f
            )
    );

    public static final List<ConsumePollutionEffect> DEFAULT_DISEASE = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new MobEffectInstance(MobEffects.POISON, SHORT_EFFECT_TIME),
                    0.5f
            ),
            new ApplyStatusEffectConsumeEffect(
                    new MobEffectInstance(TMobEffects.FEVER, LONG_EFFECT_TIME),
                    0.5f
            )
    );

    public static final List<ConsumePollutionEffect> DEFAULT_SALTINESS = List.of(
            new ApplyStatusEffectConsumeEffect(
                    new MobEffectInstance(TMobEffects.PARCHING, LONG_EFFECT_TIME, 2),
                    0.8f
            )
    );

    public static void onConsume(LivingEntity entity, ItemStack stack) {
        PollutantComponent pollutantComponent = stack.get(TDataComponentTypes.POLLUTANTS);
        Level world = entity.level();
        if (!world.isClientSide() && pollutantComponent != null) {
            WaterPollutionConfig config = Thirstful.getConfig().waterPollution();
            Consumer<ConsumePollutionEffect> effectApplier = effect -> effect.apply(world, entity, stack);

            if (pollutantComponent.checkedDirty(config)) {
                stack.getOrDefault(TDataComponentTypes.DIRTINESS_EFFECTS, DEFAULT_DIRTINESS).forEach(effectApplier);
            }

            if (pollutantComponent.checkedContaminated(config)) {
                stack.getOrDefault(TDataComponentTypes.DISEASE_EFFECTS, DEFAULT_DISEASE).forEach(effectApplier);
            }

            if (pollutantComponent.checkedSalty(config)) {
                stack.getOrDefault(TDataComponentTypes.SALTINESS_EFFECTS, DEFAULT_SALTINESS).forEach(effectApplier);
            }
        }
    }

    private PollutantEffects() {

    }
}