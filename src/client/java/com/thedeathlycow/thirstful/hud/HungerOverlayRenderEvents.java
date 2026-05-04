package com.thedeathlycow.thirstful.hud;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

public final class HungerOverlayRenderEvents {
    public static final Event<AfterHungerBar> AFTER_HUNGER_BAR = EventFactory.createArrayBacked(
            AfterHungerBar.class,
            listeners -> (graphics, player, context) -> {
                for (var listener : listeners) {
                    listener.render(graphics, player, context);
                }
            }
    );

    @FunctionalInterface
    public interface AfterHungerBar {
        void render(
                GuiGraphics graphics,
                Player player,
                HungerBarContext context
        );
    }
}