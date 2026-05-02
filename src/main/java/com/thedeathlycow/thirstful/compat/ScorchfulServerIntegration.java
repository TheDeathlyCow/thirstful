package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ScorchfulServerIntegration implements ServerThirstPlugin {
    @Override
    public boolean dehydrateFromSweating(Player player) {
        PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);
        if (thirstComponent.getThirstScaleAsFloat() < 0.5f && player.thermoo$getTemperature() > 0) {
            thirstComponent.addThirstTicks(1);
            return true;
        }

        return false;
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