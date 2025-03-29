package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.TBlockColors;
import com.thedeathlycow.thirstful.tooltip.ThirstfulTooltipAppender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class ThirstfulClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Thirstful.LOGGER.info("Initialized Thirstful Client");
        ItemTooltipCallback.EVENT.register(new ThirstfulTooltipAppender());
        TBlockColors.initialize();
    }
}