package com.thedeathlycow.thirstful.registry;

import com.google.common.collect.ImmutableSet;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.mixin.common.accessor.PoiTypeAccessor;
import com.thedeathlycow.thirstful.mixin.common.accessor.PoiTypesAccessor;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.block.state.BlockState;

public final class TPointsOfInterest {
    private static final Set<BlockState> POLLUTED_WATER_CAULDRONS = Stream.of(TBlocks.POLLUTED_WATER_CAULDRON)
            .flatMap(block -> block.getStateDefinition().getPossibleStates().stream())
            .collect(ImmutableSet.toImmutableSet());

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful POIs");

        Holder<PoiType> leatherWorkerPOI = BuiltInRegistries.POINT_OF_INTEREST_TYPE
                .getHolder(PoiTypes.LEATHERWORKER)
                .orElseThrow();

        ((PoiTypeAccessor) (Object) leatherWorkerPOI.value()).thirstful$setBlockStates(
                ImmutableSet.<BlockState>builder()
                        .addAll(leatherWorkerPOI.value().matchingStates())
                        .addAll(POLLUTED_WATER_CAULDRONS)
                        .build()
        );
        registerStates(leatherWorkerPOI, POLLUTED_WATER_CAULDRONS);
    }

    private static void registerStates(Holder<PoiType> poiTypeEntry, Set<BlockState> states) {
        states.forEach(state -> {
            Holder<PoiType> existing = PoiTypesAccessor.thirstful$getStatesToType()
                    .put(state, poiTypeEntry);
            if (existing != null) {
                throw Util.pauseInIde(new IllegalStateException(String.format(Locale.ROOT, "%s is defined in more than one PoI type", state)));
            }
        });
    }

    private TPointsOfInterest() {

    }
}