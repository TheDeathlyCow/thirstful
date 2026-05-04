package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.world.entity.player.Player;

public class ThirstfulServerThirstPlugin implements ServerThirstPlugin {
    @Override
    public boolean dehydrateFromSweating(Player player) {
        boolean makeWet = false;

        // this does not run every tick if the player gets cooled to 0 temp!
        if (player.thermoo$getTemperature() > 0) {
            PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);

            double thirstTickIncrease = 0.001;

            // player continues to get thirsty but this will no longer provide any cooling effect
            // similar to how health regen stops with a small amount of hunger loss
            if (thirstComponent.getThirstScale() >= 0.85) {
                thirstTickIncrease = 0.005;
                makeWet = true;
            }

            thirstComponent.removeThirstLevel(thirstTickIncrease, false);
        }

        return makeWet;
    }

    @Override
    public void rehydrateFromEnchantment(Player player, int waterCaptured, double rehydrationEfficiency) {
        PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);
        double thirstToAdd = (double) waterCaptured / player.thermoo$getMaxWetTicks() * rehydrationEfficiency * thirstComponent.getMaxThirstTicks();
        thirstComponent.addThirstLevel(thirstToAdd);
    }
}