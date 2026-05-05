package com.thedeathlycow.thirstful.registry;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public final class TDamageTypes {
    public static final ResourceKey<DamageType> DEHYDRATION = key("dehydration");

    private static ResourceKey<DamageType> key(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Thirstful.id(id));
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(TDamageTypes.DEHYDRATION, new DamageType("dehydration", 0.0f));
    }

    private TDamageTypes() {
    }
}