package com.thedeathlycow.thirstful.world;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public record WaterPollutants(
        float dirtiness,
        float diseaseChance,
        boolean salty
) {
    public static final BlockApiLookup<WaterPollutants, Void> LOOKUP = BlockApiLookup.get(
            Thirstful.id("water_pollutant_container"),
            WaterPollutants.class,
            Void.class
    );

    public static void intialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful pollutant lookup API");
        LOOKUP.registerFallback(WaterPollutants::find);
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

    private static WaterPollutants find(
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable BlockEntity blockEntity,
            Void context
    ) {
        float distanceChance = 1.0f;
        float dirtiness = 1.0f;
        boolean salty = false;

        RegistryEntry<Biome> biome = world.getBiome(pos);

        if (biome.isIn(TBiomeTags.HAS_SAFE_WATER)) {
            distanceChance = 0f;
        }

        if (biome.isIn(TBiomeTags.HAS_CLEAN_WATER)) {
            dirtiness = 0f;
        }

        if (biome.isIn(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new WaterPollutants(dirtiness, distanceChance, salty);
    }
}