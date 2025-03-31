package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.TBlockColors;
import com.thedeathlycow.thirstful.config.ThirstfulClientConfig;
import com.thedeathlycow.thirstful.tooltip.ThirstfulTooltipAppender;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

import java.util.function.Supplier;

public class ThirstfulClient implements ClientModInitializer {
    private static final ThirstfulClientConfig CONFIG = ConfigApi.registerAndLoadConfig(
            (Supplier<ThirstfulClientConfig>) ThirstfulClientConfig::new,
            RegisterType.CLIENT
    );

    @Override
    public void onInitializeClient() {
        Thirstful.LOGGER.info("Initialized Thirstful Client");
        ItemTooltipCallback.EVENT.register(new ThirstfulTooltipAppender());
        TBlockColors.initialize();
    }

    public static ThirstfulClientConfig getConfig() {
        return CONFIG;
    }
}