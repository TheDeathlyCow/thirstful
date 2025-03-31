package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBehavior;
import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.registry.*;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thirstful implements ModInitializer {
    public static final String MODID = "thirstful";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    private static ConfigHolder<ThirstfulConfig> configHolder;

    @Override
    public void onInitialize() {
        initialize();
    }

    public static void initialize() {
        configHolder = AutoConfig.register(ThirstfulConfig.class, JanksonConfigSerializer::new); //NOSONAR

        PollutedWaterCauldronBehavior.initialize();
        TBlocks.initialize();
        TBlockEntityTypes.initialize();
        TConsumePollutionEffects.initialize();
        TItems.initialize();
        TStatusEffects.initialize();
        TDataComponentTypes.initialize();
        WaterPollution.initialize();
        TPointsOfInterest.initialize();
        if (ModIntegration.isScorchfulLoaded()) {
            ScorchfulIntegration.initialize();
        }

        Thirstful.LOGGER.info("Initialized Thirstful");
    }

    public static ThirstfulConfig getConfig() {
        return configHolder.getConfig();
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}