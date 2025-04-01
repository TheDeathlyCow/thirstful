package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import com.thedeathlycow.thirstful.config.ThirstfulClientConfig;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TBlocks;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

public class EnglishUSGenerator extends FabricLanguageProvider {
    public EnglishUSGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        builder.add(TStatusEffects.COOLING.value(), "Cooling");
        builder.add(TStatusEffects.WARMING.value(), "Warming");
        builder.add(TStatusEffects.FEVER.value(), "Fever");

        builder.add(TBlocks.POLLUTED_WATER_CAULDRON, "Polluted Water Cauldron");

        builder.add(pollutantComponent("dirty"), "Dirty");
        builder.add(pollutantComponent("contaminated"), "Contaminated");
        builder.add(pollutantComponent("salty"), "Salty");
        builder.add(pollutantComponent("clean"), "Clean");

        builder.add(TItemTags.CAN_BE_POLLUTED, "Can be Polluted");
        builder.add(TItemTags.CAN_NOT_BE_POLLUTED, "Can not be Polluted");
        builder.add(TItemTags.CONTAMINATED_BY_DEFAULT, "Contaminated Consumables");
        builder.add(TItemTags.DIRTY_BY_DEFAULT, "Dirty Consumables");
        builder.add(TItemTags.SALTY_BY_DEFAULT, "Salty Items");

        generateConfigTranslations(builder);
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