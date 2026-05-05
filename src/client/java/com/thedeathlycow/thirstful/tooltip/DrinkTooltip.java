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