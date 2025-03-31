package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBehavior;
import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.registry.*;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class Thirstful implements ModInitializer {
    public static final String MODID = "thirstful";

    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    private static final ThirstfulConfig CONFIG = ConfigApi.registerAndLoadConfig(
            (Supplier<ThirstfulConfig>) ThirstfulConfig::new,
            RegisterType.BOTH
    );

    @Override
    public void onInitialize() {
        initialize();
    }

    public static void initialize() {
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
        return CONFIG;
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}