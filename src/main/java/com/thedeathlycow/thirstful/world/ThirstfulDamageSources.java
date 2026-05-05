package com.thedeathlycow.thirstful.world;

import com.thedeathlycow.thirstful.registry.TDamageTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public final class ThirstfulDamageSources {
    public final Registry<DamageType> damageTypes;
    private final DamageSource dehydration;

    public ThirstfulDamageSources(RegistryAccess registry) {
        this.damageTypes = registry.registryOrThrow(Registries.DAMAGE_TYPE);
        this.dehydration = this.source(TDamageTypes.DEHYDRATION);
    }

    public DamageSource dehydration() {
        return this.dehydration;
    }

    public final DamageSource source(ResourceKey<DamageType> damageTypeKey) {
        return new DamageSource(this.damageTypes.getHolderOrThrow(damageTypeKey));
    }
}