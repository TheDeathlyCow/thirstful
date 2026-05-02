package com.thedeathlycow.thirstful.mixin.common.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(PoiType.class)
public interface PointOfInterestTypeAccessor {
    @Accessor("matchingStates")
    @Mutable
    void thirstful$setBlockStates(Set<BlockState> states);
}
