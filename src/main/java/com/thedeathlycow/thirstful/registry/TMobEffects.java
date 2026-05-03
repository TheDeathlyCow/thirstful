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

    public static final Holder<MobEffect> HEAVY_FALLING = registerReference(
            "heavy_falling",
            new ThirstfulMobEffect(MobEffectCategory.HARMFUL, CommonColors.BLACK)
                    .addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, Thirstful.id("mob_effect/heavy_falling"), 1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Thirstful.id("mob_effect/heavy_falling"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful status effects");
        AllowStatusEffectCallback.EVENT.register(FeverStatusEffect::canHaveFever);
    }

    private static Holder<MobEffect> registerReference(String name, MobEffect statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Thirstful.id(name), statusEffect);
    }

    private TMobEffects() {

    }
}