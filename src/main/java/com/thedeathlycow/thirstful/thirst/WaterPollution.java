package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.tag.TBiomeTags;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public final class WaterPollution {
    public static final PollutantComponent FALLBACK = new PollutantComponent(true, true, false);

    public static final BlockApiLookup<PollutantComponent, Void> POLLUTANT_CONTAINER = BlockApiLookup.get(
            Thirstful.id("water_pollutants"),
            PollutantComponent.class,
            Void.class
    );

    public static void initialize() {
        Thirstful.LOGGER.debug("Initialized Thirstful pollutant lookup API");
        POLLUTANT_CONTAINER.registerForBlocks(WaterPollution::waterSourceLookup, Blocks.WATER);
        POLLUTANT_CONTAINER.registerFallback((world, pos, state, blockEntity, context) -> FALLBACK);
    }

    public static boolean canCarryPollutants(ItemStack stack) {
        return stack.isIn(TItemTags.CAN_BE_POLLUTED) && !stack.isIn(TItemTags.CAN_NOT_BE_POLLUTED);
    }

    private static PollutantComponent waterSourceLookup(
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable BlockEntity blockEntity,
            Void context
    ) {
        final WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();

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