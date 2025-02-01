package com.thedeathlycow.thirstful.compat;

import com.github.thedeathlycow.scorchful.api.ServerThirstPlugin;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ScorchfulServerIntegration implements ServerThirstPlugin {
    @Override
    public boolean dehydrateFromSweating(PlayerEntity player) {
        PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);
        if (thirstComponent.getThirstScaleAsFloat() < 0.5f && player.thermoo$getTemperature() > 0) {
            thirstComponent.addThirstTicks(1);
            return true;
        }

        return false;
    }

    @Override
    public void rehydrateFromEnchantment(PlayerEntity player, int waterCaptured, double rehydrationEfficiency) {
        PlayerThirstComponent thirstComponent = PlayerThirstComponent.get(player);
        int ticksToRemove = MathHelper.floor(waterCaptured * rehydrationEfficiency);

        if (ticksToRemove > 0) {
            thirstComponent.removeThirstTicks(ticksToRemove);
        }
    }
}