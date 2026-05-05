package com.thedeathlycow.thirstful.mixin.common.world;

import com.thedeathlycow.thirstful.world.ThirstfulDamageSources;
import com.thedeathlycow.thirstful.world.ThirstfulLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(Level.class)
public class LevelMixin implements ThirstfulLevel {
    @Unique
    private ThirstfulDamageSources modDamageSources;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void init(
            WritableLevelData levelData,
            ResourceKey<Level> dimension,
            RegistryAccess registryAccess,
            Holder<DimensionType> dimensionTypeRegistration,
            Supplier<ProfilerFiller> profiler,
            boolean isClientSide,
            boolean isDebug,
            long biomeZoomSeed,
            int maxChainedNeighborUpdates,
            CallbackInfo ci
    ) {
        this.modDamageSources = new ThirstfulDamageSources(registryAccess);
    }

    @Override
    public ThirstfulDamageSources thirstful$damageSources() {
        return modDamageSources;
    }
}