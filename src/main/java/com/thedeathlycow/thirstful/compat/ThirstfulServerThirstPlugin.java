package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.Scorchful;
import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;

public class ThirstfulServerThirstPlugin implements ServerThirstPlugin {
    @Override
    public boolean dehydrateFromSweating(Player player) {
        boolean makeWet = false;

        // this does not run every tick if the player gets cooled to 0 temp!
        if (player.thermoo$getTemperatureScale() >= 0.01f) {
            PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);

            double thirstTickIncrease = 0.001;

            // player continues to get thirsty but this will no longer provide any cooling effect
            // similar to how health regen stops with a small amount of hunger loss
            if (thirstComponent.getThirstScale() >= Thirstful.getConfig().thirst().requiredThirstScaleForSweat()) {
                thirstTickIncrease = 0.005;
                makeWet = true;
            }

            if (!(player.isInvulnerable() || player.getAbilities().invulnerable)) {
                thirstComponent.removeThirstLevel(thirstTickIncrease);
            }
        }

        return makeWet;
    }

    @Override
    public void rehydrateFromEnchantment(Player player, int waterCaptured, double rehydrationEfficiency) {
        PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);

        double requiredThirstScaleForSweat = Thirstful.getConfig().thirst().requiredThirstScaleForSweat();
        double maxEfficiency = Scorchful.getConfig().thirstConfig.getMaxRehydrationEfficiency();
        double maxThirst = maxEfficiency * rehydrationEfficiency * (1 - requiredThirstScaleForSweat);
        double thirstToAdd = player.getRandom().nextDouble() * maxThirst * thirstComponent.getMaxThirstTicks();

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Thirstful.LOGGER.info("Restored {} thirst from Rehydration", thirstToAdd);
        }

        thirstComponent.addThirstLevel(thirstToAdd);
    }
}