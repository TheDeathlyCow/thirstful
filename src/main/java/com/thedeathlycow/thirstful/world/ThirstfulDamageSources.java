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

package com.thedeathlycow.thirstful.world;

import com.thedeathlycow.thirstful.registry.TDamageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public final class ThirstfulDamageSources {
    public final Registry<DamageType> damageTypes;
    private final DamageSource dehydration;

    public ThirstfulDamageSources(RegistryAccess registry) {
        this.damageTypes = registry.registryOrThrow(Registries.DAMAGE_TYPE);
        this.dehydration = this.source(TDamageTypes.DEHYDRATION);
    }

    public DamageSource dehydration() {
        return this.dehydration;
    }

    public final DamageSource source(ResourceKey<DamageType> damageTypeKey) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(damageTypeKey));
    }
}