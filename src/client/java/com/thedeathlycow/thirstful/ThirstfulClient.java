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

package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.block.TBlockColors;
import com.thedeathlycow.thirstful.config.ThirstfulClientConfig;
import com.thedeathlycow.thirstful.hud.DehydratedOverlay;
import com.thedeathlycow.thirstful.hud.HungerOverlayRenderEvents;
import com.thedeathlycow.thirstful.hud.ThirstOverlay;
import com.thedeathlycow.thirstful.item.DrinkTooltipComponent;
import com.thedeathlycow.thirstful.item.component.DrinkComponent;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import com.thedeathlycow.thirstful.tooltip.DrinkTooltip;
import com.thedeathlycow.thirstful.tooltip.ThirstfulTooltipAppender;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

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
        HungerOverlayRenderEvents.AFTER_HUNGER_BAR.register(new ThirstOverlay());
        HungerOverlayRenderEvents.AFTER_HUNGER_BAR.register(new DehydratedOverlay());

        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof DrinkTooltipComponent(DrinkComponent component)) {
                return new DrinkTooltip(component);
            }

            return null;
        });

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            HudRenderCallback.EVENT.register((guiGraphics, deltaTracker) -> {
                PlayerThirstComponent thirst = PlayerThirstComponent.get(Minecraft.getInstance().player);

                guiGraphics.drawString(
                        Minecraft.getInstance().font,
                        Component.literal("Thirst level: %.4f / %.2f".formatted(thirst.getThirstLevel(), thirst.getMaxThirstTicks())),
                        10, 10,
                        0xffffff
                );
            });
        }
    }

    public static ThirstfulClientConfig getConfig() {
        return CONFIG;
    }
}