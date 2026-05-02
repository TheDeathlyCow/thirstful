package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ThirstfulServerThirstPlugin implements ServerThirstPlugin {
    @Override
    public boolean dehydrateFromSweating(Player player) {
        boolean makeWet = false;

        if (player.thermoo$getTemperature() >= 0) {
            PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);

            int thirstTickIncrease = 5;

            // player continues to get thirsty but this will no longer provide any cooling effect
            // similar to how health regen stops with a small amount of hunger loss
            if (thirstComponent.getThirstScale() <= 0.15) {
                thirstTickIncrease = 100;
                makeWet = true;
            }

            thirstComponent.addThirstTicks(Thirstful.getConfig().thirst().maxThirstTicks(thirstTickIncrease));
        }

        return makeWet;
    }

    @Override
    public void rehydrateFromEnchantment(Player player, int waterCaptured, double rehydrationEfficiency) {
        PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);
        int ticksToRemove = Mth.floor(waterCaptured * rehydrationEfficiency);

        if (ticksToRemove > 0) {
            thirstComponent.removeThirstTicks(ticksToRemove);
        }
    }
}