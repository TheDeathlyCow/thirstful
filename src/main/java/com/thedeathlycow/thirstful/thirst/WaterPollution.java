package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public final class WaterPollution {
    public static boolean canCarryPollutants(ItemStack stack) {
        return stack.is(TItemTags.CAN_BE_POLLUTED) && !stack.is(TItemTags.CAN_NOT_BE_POLLUTED);
    }

    public static PollutantComponent findPollutants(Level world, BlockPos pos) {
        boolean dirty = true;
        boolean contaminated = true;
        boolean salty = false;

        Holder<Biome> biome = world.getBiome(pos);

        if (biome.is(TBiomeTags.HAS_CLEAN_WATER)) {
            dirty = false;
        }

        if (biome.is(TBiomeTags.HAS_SAFE_WATER)) {
            contaminated = false;
        }

        if (biome.is(TBiomeTags.HAS_SALTY_WATER)) {
            salty = true;
        }

        return new PollutantComponent(dirty, contaminated, salty);
    }

    private WaterPollution() {

    }
}