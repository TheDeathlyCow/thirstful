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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector2i;

public class ThirstOverlay implements HungerOverlayRenderEvents.AfterHungerBar {
    public static final ResourceLocation THIRST_HAUNCH = Thirstful.id("textures/gui/thirst_haunch.png");
    public static final ResourceLocation THIRST_HAUNCH_HALF = Thirstful.id("textures/gui/thirst_haunch_half.png");

    public static final int TEXTURE_WIDTH = 9;
    public static final int TEXTURE_HEIGHT = 9;

    @Override
    public void render(GuiGraphics graphics, Player player, HungerBarContext context) {

        final long thirstHalfHaunches = getThirstHalfHaunches(player);
        final long thirstHaunches = getNumThirstHaunches(thirstHalfHaunches);
        final boolean drawHalfHaunch = thirstHalfHaunches % 2 != 0;

        long haunchesRendered = 0;

        for (Vector2i position : context.positions().reversed()) {
            if (haunchesRendered >= thirstHaunches) {
                break;
            }

            int x = position.x();
            int y = position.y();

            if (drawHalfHaunch && haunchesRendered == thirstHaunches - 1) {
                graphics.blit(
                        THIRST_HAUNCH_HALF,
                        x, y,
                        0, 0,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT
                );
            } else {
                graphics.blit(
                        THIRST_HAUNCH,
                        x, y,
                        0, 0,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT,
                        TEXTURE_WIDTH, TEXTURE_HEIGHT
                );
            }

            haunchesRendered++;
        }
    }

    private static long getThirstHalfHaunches(Player player) {
        PlayerThirstComponent thirst = PlayerThirstComponent.get(player);
        double thirstProgress = thirst.getThirstScale();
        return Mth.ceil(thirstProgress * 20);
    }

    private static long getNumThirstHaunches(long thirstHalfHaunches) {
        return Mth.ceil(thirstHalfHaunches / 2.0);
    }
}