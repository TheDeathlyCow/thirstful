package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public final class TBiomeTags {

    public static final TagKey<Biome> HAS_SALTY_WATER = key("has_salty_water");
    public static final TagKey<Biome> HAS_CLEAN_WATER = key("has_clean_water");
    public static final TagKey<Biome> HAS_SAFE_WATER = key("has_safe_water");

    private static TagKey<Biome> key(String id) {
        return TagKey.of(RegistryKeys.BIOME, Thirstful.id(id));
    }


    private TBiomeTags() {

    }
}