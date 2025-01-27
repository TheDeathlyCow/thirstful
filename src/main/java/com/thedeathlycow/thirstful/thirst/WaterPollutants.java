package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
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

    public void applyToStack(ItemStack stack) {
        PollutantComponent purityComponent = stack.getOrDefault(
                TDataComponentTypes.POLLUTANTS,
                PollutantComponent.DEFAULT
        );

        stack.set(
                TDataComponentTypes.POLLUTANTS,
                purityComponent.copy(this.dirtiness, this.diseaseChance, this.salty)
        );
    }

    public static WaterPollutants lookup(World world, BlockPos pos) {
        WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();

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
            diseaseChance = config.extraDirtyWaterDirtiness();
        } else if (biome.isIn(TBiomeTags.HAS_CLEAN_WATER)) {
            dirtiness = 0f;
        }

        if (biome.isIn(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new WaterPollutants(dirtiness, diseaseChance, salty);
    }
}