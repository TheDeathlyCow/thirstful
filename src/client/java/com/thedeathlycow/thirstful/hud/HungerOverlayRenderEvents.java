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