package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class TBiomeTags {

    public static final TagKey<Biome> HAS_SALTY_WATER = key("has_salty_water");
    public static final TagKey<Biome> HAS_CLEAN_WATER = key("has_clean_water");
    public static final TagKey<Biome> HAS_SAFE_WATER = key("has_safe_water");

    private static TagKey<Biome> key(String id) {
        return TagKey.create(Registries.BIOME, Thirstful.id(id));
    }


    private TBiomeTags() {

    }
}