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

import com.github.thedeathlycow.scorchful.registry.tag.SItemTags;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public TItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(TItemTags.THIRST_RESTORING_DRINK)
                .addOptionalTag(SItemTags.IS_HYDRATING)
                .addOptionalTag(ConventionalItemTags.JUICE_DRINKS)
                .addOptionalTag(ConventionalItemTags.MILK_DRINKS)
                .addOptionalTag(ConventionalItemTags.WATER_DRINKS)
                .addOptionalTag(ConventionalItemTags.WATERY_DRINKS);

        getOrCreateTagBuilder(TItemTags.THIRST_RESTORING_SNACK)
                .addOptionalTag(SItemTags.IS_SUSTAINING)
                .addOptionalTag(SItemTags.IS_REFRESHING)
                .addOptionalTag(ConventionalItemTags.RAW_FISH_FOODS)
                .addOptionalTag(ConventionalItemTags.SOUP_FOODS)
                .addOptionalTag(ConventionalItemTags.BERRY_FOODS)
                .addOptionalTag(ConventionalItemTags.VEGETABLE_FOODS)
                .addOptionalTag(ConventionalItemTags.FRUIT_FOODS);

        getOrCreateTagBuilder(TItemTags.CAN_BE_POLLUTED)
                .add(Items.GLASS_BOTTLE)
                .addOptionalTag(TItemTags.SALTY_BY_DEFAULT)
                .addOptionalTag(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(TItemTags.DIRTY_BY_DEFAULT)
                .addOptionalTag(ConventionalItemTags.DRINKS)
                .addOptionalTag(ConventionalItemTags.POTIONS)
                .addOptionalTag(ConventionalItemTags.FOODS)
                .addOptionalTag(ConventionalItemTags.BUCKETS)
                .addOptionalTag(ConventionalItemTags.DRINK_CONTAINING_BOTTLE)
                .addOptionalTag(ConventionalItemTags.DRINK_CONTAINING_BUCKET);

        getOrCreateTagBuilder(TItemTags.CAN_NOT_BE_POLLUTED)
                .addOptionalTag(ConventionalItemTags.LAVA_BUCKETS);

        getOrCreateTagBuilder(TItemTags.CONTAMINATED_BY_DEFAULT)
                .addOptionalTag(ConventionalItemTags.MILK_DRINKS)
                .addOptionalTag(ConventionalItemTags.RAW_MEAT_FOODS)
                .addOptionalTag(ConventionalItemTags.FOOD_POISONING_FOODS)
                .addOptionalTag(ConventionalItemTags.MILK_BUCKETS);

        getOrCreateTagBuilder(TItemTags.SALTY_BY_DEFAULT);

        getOrCreateTagBuilder(TItemTags.DIRTY_BY_DEFAULT);
    }
}