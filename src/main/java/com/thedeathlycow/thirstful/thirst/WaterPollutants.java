package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import com.thedeathlycow.thirstful.registry.tag.TBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public record WaterPollutants(
        float dirtiness,
        float diseaseChance,
        boolean salty
) {

    public static void intialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful pollutant lookup API");
    }

    public static WaterPollutants lookup(World world, BlockState state, BlockPos pos) {
        WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();
        if (state.isIn(TBlockTags.IS_WATER_STORAGE)) {
            return lookupCauldron(world, state, pos, config);
        } else {
            return lookupWaterSource(world, state, pos, config);
        }
    }

    private static WaterPollutants lookupWaterSource(World world, BlockState state, BlockPos pos, WaterPollutionConfig config) {
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

        return new WaterPollutants(dirtiness, diseaseChance, salty);
    }

    private static WaterPollutants lookupCauldron(World world, BlockState state, BlockPos pos, WaterPollutionConfig config) {
        return new WaterPollutants(0f, 0f, false);
    }
}