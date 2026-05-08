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

package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public final class WaterPollution {
    public static boolean canCarryPollutants(ItemStack stack) {
        return stack.is(TItemTags.CAN_BE_POLLUTED) && !stack.is(TItemTags.CAN_NOT_BE_POLLUTED);
    }

    public static PollutantComponent findPollutants(Level world, BlockPos pos) {
        boolean dirty = true;
        boolean contaminated = true;
        boolean salty = false;

        Holder<Biome> biome = world.getBiome(pos);

        if (biome.is(TBiomeTags.HAS_CLEAN_WATER)) {
            dirty = false;
        }

        if (biome.is(TBiomeTags.HAS_SAFE_WATER)) {
            contaminated = false;
        }

        if (biome.is(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new PollutantComponent(dirty, contaminated, salty);
    }

    private WaterPollution() {

    }
}