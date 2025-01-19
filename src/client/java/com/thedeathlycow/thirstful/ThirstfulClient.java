package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.tooltip.PurificationTooltip;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class ThirstfulClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ItemTooltipCallback.EVENT.register(new PurificationTooltip());

		Thirstful.LOGGER.info("Initialized Thirstful Client");
	}
}