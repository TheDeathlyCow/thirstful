package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public final class TPotions {
    private static final int SHORT_DURATION = 1800;
    private static final int STANDARD_DURATION = 3600;
    private static final int LONG_DURATION = 9600;

    private static final String PARCHING_NAME = Thirstful.MODID + ".parching";

    public static final Holder<Potion> PARCHING = register(
            "parching",
            new Potion(PARCHING_NAME, new MobEffectInstance(TMobEffects.PARCHING, STANDARD_DURATION))
    );
    public static final Holder<Potion> LONG_PARCHING = register(
            "long_parching",
            new Potion(PARCHING_NAME, new MobEffectInstance(TMobEffects.PARCHING, LONG_DURATION))
    );
    public static final Holder<Potion> STRONG_PARCHING = register(
            "strong_parching",
            new Potion(PARCHING_NAME, new MobEffectInstance(TMobEffects.PARCHING, SHORT_DURATION, 1))
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized thirstful potions");

        FabricBrewingRecipeRegistryBuilder.BUILD.register(
                builder -> {
                    builder.addMix(Potions.AWKWARD, Items.BONE_MEAL, TPotions.PARCHING);

                    // TODO (26.1): use dust if scorchful is installed

                    builder.addMix(TPotions.PARCHING, Items.REDSTONE, TPotions.LONG_PARCHING);
                    builder.addMix(TPotions.PARCHING, Items.GLOWSTONE_DUST, TPotions.STRONG_PARCHING);
                }
        );
    }

    private static Holder<Potion> register(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Thirstful.id(id), potion);
    }

    private TPotions() {

    }
}