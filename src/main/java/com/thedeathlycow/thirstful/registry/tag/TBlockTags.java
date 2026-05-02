package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class TBlockTags {
    private static TagKey<Block> key(String id) {
        return TagKey.create(Registries.BLOCK, Thirstful.id(id));
    }
    private TBlockTags() {

    }
}