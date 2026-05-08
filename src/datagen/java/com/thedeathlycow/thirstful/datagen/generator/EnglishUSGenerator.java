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

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.command.ThirstfulCommand;
import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import com.thedeathlycow.thirstful.config.ThirstfulClientConfig;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.mixin.common.accessor.PotionAccessor;
import com.thedeathlycow.thirstful.registry.TBlocks;
import com.thedeathlycow.thirstful.registry.TDamageTypes;
import com.thedeathlycow.thirstful.registry.TMobEffects;
import com.thedeathlycow.thirstful.registry.TPotions;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

public class EnglishUSGenerator extends FabricLanguageProvider {
    public EnglishUSGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider wrapperLookup, TranslationBuilder builder) {
        HolderLookup.RegistryLookup<DamageType> damageTypes = wrapperLookup.lookupOrThrow(Registries.DAMAGE_TYPE);

        builder.add(TMobEffects.COOLING.value(), "Cooling");
        builder.add(TMobEffects.WARMING.value(), "Warming");
        builder.add(TMobEffects.FEVER.value(), "Fever");
        builder.add(TMobEffects.PARCHING.value(), "Parching");

        builder.add(TBlocks.POLLUTED_WATER_CAULDRON, "Polluted Water Cauldron");
        builder.add(TBlocks.MEAT_STILL, "Meat Still");

        builder.add(pollutantComponent("dirty"), "Dirty");
        builder.add(pollutantComponent("contaminated"), "Contaminated");
        builder.add(pollutantComponent("salty"), "Salty");
        builder.add(pollutantComponent("clean"), "Clean");

        builder.add(TItemTags.CAN_BE_POLLUTED, "Can be Polluted");
        builder.add(TItemTags.CAN_NOT_BE_POLLUTED, "Can not be Polluted");
        builder.add(TItemTags.CONTAMINATED_BY_DEFAULT, "Contaminated Consumables");
        builder.add(TItemTags.DIRTY_BY_DEFAULT, "Dirty Consumables");
        builder.add(TItemTags.SALTY_BY_DEFAULT, "Salty Items");

        builder.add(damageType(TDamageTypes.DEHYDRATION, damageTypes), "%1$s died from thirst");
        builder.add(damageTypePlayer(TDamageTypes.DEHYDRATION, damageTypes), "%1$s died from thirst while trying to escape %2$s");

        builder.add(potionItem(Items.POTION, TPotions.PARCHING), "Potion of Parching");
        builder.add(potionItem(Items.SPLASH_POTION, TPotions.PARCHING), "Splash Potion of Parching");
        builder.add(potionItem(Items.LINGERING_POTION, TPotions.PARCHING), "Lingering Potion of Parching");
        builder.add(potionItem(Items.TIPPED_ARROW, TPotions.PARCHING), "Arrow of Parching");

        builder.add(ThirstfulCommand.ABOUT_DESC_ID, "Thirstful is free software distributed under the terms of the GNU Affero General Public License version 3. You may access a copy of the source code for the version of Thirstful used on this server at the following link:");

        generateConfigTranslations(builder);
    }

    private static String damageType(ResourceKey<DamageType> key, HolderLookup.RegistryLookup<DamageType> damageTypes) {
        return "death.attack." + damageTypes.getOrThrow(key).value().msgId();
    }

    private static String damageTypePlayer(ResourceKey<DamageType> key, HolderLookup.RegistryLookup<DamageType> damageTypes) {
        return damageType(key, damageTypes) + ".player";
    }

    private String potionItem(Item item, Holder<Potion> potion) {
        return item.getDescriptionId() + ".effect." + ((PotionAccessor) potion.value()).thirstful$name();
    }

    private static String pollutantComponent(String key) {
        return "item.thirstful.pollutant." + key;
    }

    private void generateConfigTranslations(TranslationBuilder builder) {
        builder.add("thirstful.common", "Common Config");
        builder.add("thirstful.client", "Client Config");

        generateConfigSectionTranslations(builder, "thirstful.common", ThirstfulConfig.class);
        generateConfigSectionTranslations(builder, "thirstful.common.statusEffect", StatusEffectConfig.class);
        generateConfigSectionTranslations(builder, "thirstful.common.thirst", ThirstConfig.class);
        generateConfigSectionTranslations(builder, "thirstful.common.waterPollution", WaterPollutionConfig.class);

        generateConfigSectionTranslations(builder, "thirstful.client", ThirstfulClientConfig.class);
        generateConfigSectionTranslations(builder, "thirstful.client.color", ColorConfig.class);
    }

    private void generateConfigSectionTranslations(
            TranslationBuilder builder,
            String prefix,
            Class<?> configClass
    ) {
        for (Field field : configClass.getDeclaredFields()) {
            String nameKey = String.format("%s.%s", prefix, field.getName());

            OptionName nameData = field.getAnnotation(OptionName.class);
            if (nameData != null) {
                builder.add(nameKey, nameData.value());
            } else {
                Thirstful.LOGGER.error("Option name missing for {}", nameKey);
            }

            Comment commentData = field.getAnnotation(Comment.class);
            String commentKey = nameKey + ".desc";

            if (commentData != null) {
                String comment = commentData.value();
                builder.add(commentKey, comment);
            } else if (field.getAnnotation(NoComment.class) == null) {
                Thirstful.LOGGER.warn("Missing comment for {}", commentKey);
            }
        }
    }
}