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

package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class TBiomeTags {

    public static final TagKey<Biome> HAS_SALTY_WATER = key("has_salty_water");
    public static final TagKey<Biome> HAS_CLEAN_WATER = key("has_clean_water");
    public static final TagKey<Biome> HAS_SAFE_WATER = key("has_safe_water");

    private static TagKey<Biome> key(String id) {
        return TagKey.create(Registries.BIOME, Thirstful.id(id));
    }


    private TBiomeTags() {

    }
}