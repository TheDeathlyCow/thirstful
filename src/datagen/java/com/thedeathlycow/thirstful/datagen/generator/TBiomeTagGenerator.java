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

package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import java.util.concurrent.CompletableFuture;

public class TBiomeTagGenerator extends FabricTagProvider<Biome> {
    public TBiomeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        getOrCreateTagBuilder(TBiomeTags.HAS_CLEAN_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN);

        getOrCreateTagBuilder(TBiomeTags.HAS_SAFE_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_RIVER);

        getOrCreateTagBuilder(TBiomeTags.HAS_SALTY_WATER)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN)
                .addOptionalTag(ConventionalBiomeTags.IS_BEACH)
                .addOptionalTag(ConventionalBiomeTags.IS_STONY_SHORES)
                .addOptionalTag(ConventionalBiomeTags.IS_SWAMP);
    }
}