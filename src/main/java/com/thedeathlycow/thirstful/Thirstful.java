package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBehavior;
import com.thedeathlycow.thirstful.command.ThirstfulCommand;
import com.thedeathlycow.thirstful.compat.ModIntegration;
import com.thedeathlycow.thirstful.compat.ScorchfulIntegration;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.registry.*;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ContactInformation;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.resources.ResourceLocation;
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
        String sourceLink = getSourceLink();
        Thirstful.LOGGER.info("Thirstful source link set to {}", sourceLink);

        PollutedWaterCauldronBehavior.initialize();
        TBlocks.initialize();
        TDataComponentTypes.initialize();
        TBlockEntityTypes.initialize();
        TConsumePollutionEffects.initialize();
        TItems.initialize();
        TMobEffects.initialize();
        TPotions.initialize();
        TPointsOfInterest.initialize();

        if (ModIntegration.isScorchfulLoaded()) {
            ScorchfulIntegration.initialize();
        }

        CommandRegistrationCallback.EVENT.register(new ThirstfulCommand());

        Thirstful.LOGGER.info("Initialized Thirstful");
    }

    public static ThirstfulConfig getConfig() {
        return CONFIG;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static String getSourceLink() {
        final ContactInformation modMeta = FabricLoader.getInstance()
                .getModContainer(MODID)
                .orElseThrow()
                .getMetadata()
                .getContact();

        final String sources = modMeta.get("sources").orElse(null);

        if (sources != null) {
            return sources;
        } else {
            throw new IllegalStateException("Thirstful's source code link has not been set in fabric.mod.json. If your server is using a fork of Thirstful, note that you MUST publish your source code publicly and make it available to users of your server per the terms of Thirstful's license. Please make the source code for your version of Thirstful public by setting the `sources` key in a `contact` block of your fabric.mod.json.");
        }
    }
}