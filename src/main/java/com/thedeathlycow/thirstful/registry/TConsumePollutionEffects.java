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

package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.consume.ApplyStatusEffectConsumeEffect;
import com.thedeathlycow.thirstful.item.consume.ConsumePollutionEffect;
import net.minecraft.core.Registry;

public final class TConsumePollutionEffects {
    public static final ConsumePollutionEffect.Type<ApplyStatusEffectConsumeEffect> APPLY_STATUS_EFFECT = register(
            "apply_status_effect",
            new ConsumePollutionEffect.Type<>(
                    ApplyStatusEffectConsumeEffect.CODEC,
                    ApplyStatusEffectConsumeEffect.PACKET_CODEC
            )
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful consume pollution copyEffect types");
    }

    private static <T extends ApplyStatusEffectConsumeEffect> ConsumePollutionEffect.Type<T> register(
            String name,
            ConsumePollutionEffect.Type<T> type
    ) {
        return Registry.register(TRegistries.CONSUME_POLLUTION_EFFECT_TYPE, Thirstful.id(name), type);
    }

    private TConsumePollutionEffects() {

    }
}