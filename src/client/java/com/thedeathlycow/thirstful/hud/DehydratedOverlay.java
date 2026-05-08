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

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector2i;

public class DehydratedOverlay implements HungerOverlayRenderEvents.AfterHungerBar {
    public static final ResourceLocation TEXTURE = Thirstful.id("textures/gui/dehydrated_haunch.png");

    public static final int TEXTURE_WIDTH = 9;
    public static final int TEXTURE_HEIGHT = 9;

    @Override
    public void render(GuiGraphics graphics, Player player, HungerBarContext context) {
        if (!PlayerThirstComponent.get(player).isDehydrated()) {
            return;
        }

        for (Vector2i position : context.positions().reversed()) {
            int x = position.x();
            int y = position.y();

            graphics.blit(
                    TEXTURE,
                    x, y,
                    0, 0,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT,
                    TEXTURE_WIDTH, TEXTURE_HEIGHT
            );
        }
    }
}