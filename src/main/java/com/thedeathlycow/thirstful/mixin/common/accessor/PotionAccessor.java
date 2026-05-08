package com.thedeathlycow.thirstful.mixin.common.accessor;

import net.minecraft.world.item.alchemy.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Potion.class)
public interface PotionAccessor {
    // TODO: remove this in 26.1
    @Accessor("name")
    String thirstful$name();
}