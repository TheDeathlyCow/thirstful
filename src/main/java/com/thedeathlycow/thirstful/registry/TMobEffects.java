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
import com.thedeathlycow.thirstful.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public final class TMobEffects {
    public static final Holder<MobEffect> COOLING = registerReference(
            "cooling",
            new CoolingMobEffect(0x4832a8)
    );

    public static final Holder<MobEffect> WARMING = registerReference(
            "warming",
            new WarmingMobEffect(0xe35c02)
    );

    public static final Holder<MobEffect> FEVER = registerReference(
            "fever",
            new FeverMobEffect(0xf7442d)
    );

    public static final Holder<MobEffect> PARCHING = registerReference(
            "parching",
            new ParchingMobEffect(0xe8d9ca)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful status effects");
        AllowStatusEffectCallback.EVENT.register(FeverMobEffect::canHaveFever);
    }

    private static Holder<MobEffect> registerReference(String name, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Thirstful.id(name), statusEffect);
    }

    private TMobEffects() {

    }
}