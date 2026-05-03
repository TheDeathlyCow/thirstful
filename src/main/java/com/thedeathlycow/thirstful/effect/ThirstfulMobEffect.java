package com.thedeathlycow.thirstful.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

// This class purely exists to expose the mob effect constructor as public
public class ThirstfulMobEffect extends MobEffect {
    public ThirstfulMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public ThirstfulMobEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }
}