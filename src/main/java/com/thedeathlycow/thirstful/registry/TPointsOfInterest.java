/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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