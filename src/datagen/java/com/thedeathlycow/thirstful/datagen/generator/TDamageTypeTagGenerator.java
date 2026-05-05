package com.thedeathlycow.thirstful.datagen.generator;

import com.thedeathlycow.thirstful.registry.TDamageTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class TDamageTypeTagGenerator extends FabricTagProvider<DamageType> {
    public TDamageTypeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        tag(DamageTypeTags.NO_KNOCKBACK)
                .add(TDamageTypes.DEHYDRATION);

        tag(DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES)
                .add(TDamageTypes.DEHYDRATION);

        tag(DamageTypeTags.BYPASSES_WOLF_ARMOR)
                .add(TDamageTypes.DEHYDRATION);
    }
}