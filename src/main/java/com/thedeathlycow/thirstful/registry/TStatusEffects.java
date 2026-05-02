package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.effect.AllowStatusEffectCallback;
import com.thedeathlycow.thirstful.effect.CoolingStatusEffect;
import com.thedeathlycow.thirstful.effect.FeverStatusEffect;
import com.thedeathlycow.thirstful.effect.WarmingStatusEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.CommonColors;
import net.minecraft.world.effect.MobEffect;

public final class TStatusEffects {
    public static final Holder<MobEffect> COOLING = registerReference(
            "cooling",
            new CoolingStatusEffect(CommonColors.BLUE)
    );

    public static final Holder<MobEffect> WARMING = registerReference(
        "warming",
            new WarmingStatusEffect(CommonColors.RED)
    );

    public static final Holder<MobEffect> FEVER = registerReference(
            "fever",
            new FeverStatusEffect(CommonColors.RED)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful status effects");
        AllowStatusEffectCallback.EVENT.register(FeverStatusEffect::canHaveFever);
    }

    private static Holder<MobEffect> registerReference(String name, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Thirstful.id(name), statusEffect);
    }

    private TStatusEffects() {

    }
}