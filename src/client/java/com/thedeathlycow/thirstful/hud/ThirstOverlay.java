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
        return Math.round(thirstProgress * 20);
    }

    private static long getNumThirstHaunches(long thirstHalfHaunches) {
        return Mth.ceil(thirstHalfHaunches / 2.0);
    }
}