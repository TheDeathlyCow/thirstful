package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public final class WaterPollution {
    public static final BlockApiLookup<PollutantContainer, Void> POLLUTANT_CONTAINER = BlockApiLookup.get(
            Thirstful.id("water_pollutants"),
            PollutantContainer.class,
            Void.class
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful pollutant lookup API");
        POLLUTANT_CONTAINER.registerForBlocks(WaterPollution::waterSourceLookup, Blocks.WATER);
        POLLUTANT_CONTAINER.registerFallback(WaterPollution::fallbackLookup);
    }

    private static PollutantContainer waterSourceLookup(
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable BlockEntity blockEntity,
            Void context
    ) {
        final WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();

        float diseaseChance = config.defaultWaterDiseaseChance();
        float dirtiness = config.defaultWaterDirtiness();
        boolean salty = false;

        RegistryEntry<Biome> biome = world.getBiome(pos);

        if (biome.isIn(TBiomeTags.HAS_VERY_UNSAFE_WATER)) {
            diseaseChance = config.extraContaminatedWaterDiseaseChance();
        } else if (biome.isIn(TBiomeTags.HAS_SAFE_WATER)) {
            diseaseChance = 0f;
        }

        if (biome.isIn(TBiomeTags.HAS_VERY_DIRTY_WATER)) {
            dirtiness = config.extraDirtyWaterDirtiness();
        } else if (biome.isIn(TBiomeTags.HAS_CLEAN_WATER)) {
            dirtiness = 0f;
        }

        if (biome.isIn(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new PollutantContainer(dirtiness, diseaseChance, salty);
    }

    private static PollutantContainer fallbackLookup(
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable BlockEntity blockEntity,
            Void context
    ) {
        final WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();
        return new PollutantContainer(
                config.defaultWaterDirtiness(),
                config.defaultWaterDiseaseChance(),
                false
        );
    }

    private WaterPollution() {

    }
}