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

package com.thedeathlycow.thirstful.datagen;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.datagen.generator.*;
import com.thedeathlycow.thirstful.registry.TDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ThirstfulDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(BootstrappedRegistryGenerator::new);

        TBlockTagGenerator blockTags = pack.addProvider(TBlockTagGenerator::new);
        pack.addProvider((output, wrapperLookup) -> new TItemTagGenerator(output, wrapperLookup, blockTags));
        pack.addProvider(TBiomeTagGenerator::new);
        pack.addProvider(EnglishUSGenerator::new);
        pack.addProvider(RecipeGenerator::new);
        pack.addProvider(TDamageTypeTagGenerator::new);
    }

    public static ResourceLocation commonId(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    public static ResourceLocation scorchfulId(String path) {
        return ResourceLocation.fromNamespaceAndPath("scorchful", path);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return Thirstful.MODID;
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DAMAGE_TYPE, TDamageTypes::bootstrap);
    }
}
