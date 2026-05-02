package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.TBlockColors;
import com.thedeathlycow.thirstful.config.ThirstfulClientConfig;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import com.thedeathlycow.thirstful.tooltip.ThirstfulTooltipAppender;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ColorRGBA;

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
        HudRenderCallback.EVENT.register((guiGraphics, deltaTracker) -> {
            PlayerThirstComponent thirst = PlayerThirstComponent.get(Minecraft.getInstance().player);

            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    Component.literal("Thirst: %d / %d".formatted(thirst.getThirstTicks(), thirst.getMaxThirstTicks())),
                    10, 10,
                    0xffffff
            );
        });
    }

    public static ThirstfulClientConfig getConfig() {
        return CONFIG;
    }
}