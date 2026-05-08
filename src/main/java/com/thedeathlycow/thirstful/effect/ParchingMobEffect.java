package com.thedeathlycow.thirstful.effect;

import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ParchingMobEffect extends ThirstfulMobEffect {
    public ParchingMobEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            PlayerThirstComponent thirst = PlayerThirstComponent.get(player);
            thirst.removeThirstLevel(0.003 * (amplifier + 1));
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}