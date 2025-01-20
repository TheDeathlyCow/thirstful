package com.thedeathlycow.thirstful.world;

import com.thedeathlycow.thirstful.Thirstful;
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

public final class PollutantLookup {

    public static final BlockApiLookup<WaterPollutantContainer, Void> API = BlockApiLookup.get(
            Thirstful.id("water_pollutant_container"),
            WaterPollutantContainer.class,
            Void.class
    );

    public static void intialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful pollutant lookup API");
        PollutantLookup.API.registerForBlocks(PollutantLookup::find, Blocks.WATER);
    }

    private static WaterPollutantContainer find(
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable BlockEntity blockEntity,
            Void context
    ) {
        boolean contaminated = false;
        boolean dirty = false;
        boolean salty = false;

        RegistryEntry<Biome> biome = world.getBiome(pos);

        if (biome.isIn(TBiomeTags.HAS_CONTAMINATED_WATER)) {
            contaminated = true;
        }

        if (biome.isIn(TBiomeTags.HAS_DIRTY_WATER)) {
            dirty = true;
        }

        if (biome.isIn(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new WaterPollutantContainer(contaminated, dirty, salty);
    }

    private PollutantLookup() {

    }
}