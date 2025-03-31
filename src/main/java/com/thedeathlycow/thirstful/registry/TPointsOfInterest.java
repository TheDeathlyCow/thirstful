package com.thedeathlycow.thirstful.registry;

import com.google.common.collect.ImmutableSet;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.mixin.common.accessor.PointOfInterestTypeAccessor;
import com.thedeathlycow.thirstful.mixin.common.accessor.PointOfInterestTypesAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Util;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public final class TPointsOfInterest {
    private static final Set<BlockState> POLLUTED_WATER_CAULDRONS = Stream.of(TBlocks.POLLUTED_WATER_CAULDRON)
            .flatMap(block -> block.getStateManager().getStates().stream())
            .collect(ImmutableSet.toImmutableSet());

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful POIs");

        RegistryEntry<PointOfInterestType> leatherWorkerPOI = Registries.POINT_OF_INTEREST_TYPE
                .getEntry(PointOfInterestTypes.LEATHERWORKER)
                .orElseThrow();

        ((PointOfInterestTypeAccessor) (Object) leatherWorkerPOI.value()).thirstful$setBlockStates(
                ImmutableSet.<BlockState>builder()
                        .addAll(leatherWorkerPOI.value().blockStates())
                        .addAll(POLLUTED_WATER_CAULDRONS)
                        .build()
        );
        registerStates(leatherWorkerPOI, POLLUTED_WATER_CAULDRONS);
    }

    private static void registerStates(RegistryEntry<PointOfInterestType> poiTypeEntry, Set<BlockState> states) {
        states.forEach(state -> {
            RegistryEntry<PointOfInterestType> existing = PointOfInterestTypesAccessor.thirstful$getStatesToType()
                    .put(state, poiTypeEntry);
            if (existing != null) {
                throw Util.throwOrPause(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", state)));
            }
        });
    }

    private TPointsOfInterest() {

    }
}