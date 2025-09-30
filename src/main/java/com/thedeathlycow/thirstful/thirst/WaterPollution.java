package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public final class WaterPollution {
    public static boolean canCarryPollutants(ItemStack stack) {
        return stack.isIn(TItemTags.CAN_BE_POLLUTED) && !stack.isIn(TItemTags.CAN_NOT_BE_POLLUTED);
    }

    public static PollutantComponent findPollutants(World world, BlockPos pos) {
        boolean dirty = true;
        boolean contaminated = true;
        boolean salty = false;

        RegistryEntry<Biome> biome = world.getBiome(pos);

        if (biome.isIn(TBiomeTags.HAS_CLEAN_WATER)) {
            dirty = false;
        }

        if (biome.isIn(TBiomeTags.HAS_SAFE_WATER)) {
            contaminated = false;
        }

        if (biome.isIn(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new PollutantComponent(dirty, contaminated, salty);
    }

    private WaterPollution() {

    }
}