package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.effect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.CommonColors;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class TMobEffects {
    public static final Holder<MobEffect> COOLING = registerReference(
            "cooling",
            new CoolingMobEffect(0x4832a8)
    );

    public static final Holder<MobEffect> WARMING = registerReference(
            "warming",
            new WarmingMobEffect(0xe35c02)
    );

    public static final Holder<MobEffect> FEVER = registerReference(
            "fever",
            new FeverMobEffect(0xf7442d)
    );

    public static final Holder<MobEffect> PARCHED = registerReference(
            "parched",
            new ParchedMobEffect(0xfad975)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful status effects");
        AllowStatusEffectCallback.EVENT.register(FeverMobEffect::canHaveFever);
    }

    private static Holder<MobEffect> registerReference(String name, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Thirstful.id(name), statusEffect);
    }

    private TMobEffects() {

    }
}