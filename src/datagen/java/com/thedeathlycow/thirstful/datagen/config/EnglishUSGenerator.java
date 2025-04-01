package com.thedeathlycow.thirstful.datagen.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import com.thedeathlycow.thirstful.config.ThirstfulClientConfig;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
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
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("thirstful.common", "Common Config");
        translationBuilder.add("thirstful.client", "Client Config");

        generateConfigTranslations(translationBuilder, "thirstful.common", ThirstfulConfig.class);
        generateConfigTranslations(translationBuilder, "thirstful.common.statusEffect", StatusEffectConfig.class);
        generateConfigTranslations(translationBuilder, "thirstful.common.thirst", ThirstConfig.class);
        generateConfigTranslations(translationBuilder, "thirstful.common.waterPollution", WaterPollutionConfig.class);

        generateConfigTranslations(translationBuilder, "thirstful.client", ThirstfulClientConfig.class);
        generateConfigTranslations(translationBuilder, "thirstful.client.color", ColorConfig.class);
    }

    private void generateConfigTranslations(
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