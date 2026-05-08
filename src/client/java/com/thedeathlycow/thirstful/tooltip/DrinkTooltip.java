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

package com.thedeathlycow.thirstful.tooltip;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.component.DrinkComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public record DrinkTooltip(DrinkComponent drink) implements ClientTooltipComponent {
    private static final ResourceLocation FULL_TEXTURE = Thirstful.id("drink");
    private static final ResourceLocation HALF_TEXTURE = Thirstful.id("drink_half");

    private static final int TEXTURE_WIDTH = 9;
    private static final int TEXTURE_HEIGHT = 11;

    @Override
    public int getHeight() {
        return TEXTURE_HEIGHT;
    }

    @Override
    public int getWidth(Font font) {
        return TEXTURE_WIDTH * Mth.ceil(drink.water());
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        int drinks = Mth.ceil(drink.water());

        for (int i = 0; i < drinks; i++) {
            int sx = x + (i * TEXTURE_WIDTH);

            if (i == drinks - 1 && drinks != Mth.floor(drink.water())) {
                graphics.blitSprite(HALF_TEXTURE, sx, y, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            } else {
                graphics.blitSprite(FULL_TEXTURE, sx, y, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            }
        }
    }
}