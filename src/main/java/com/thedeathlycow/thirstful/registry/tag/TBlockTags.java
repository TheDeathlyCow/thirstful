package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class TBlockTags {
    private static TagKey<Block> key(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Thirstful.id(id));
    }
    private TBlockTags() {

    }
}