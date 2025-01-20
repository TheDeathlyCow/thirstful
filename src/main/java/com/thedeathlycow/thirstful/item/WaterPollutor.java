package com.thedeathlycow.thirstful.item;

import com.thedeathlycow.thirstful.mixin.common.accessor.ItemAccessor;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.world.PollutantLookup;
import com.thedeathlycow.thirstful.world.WaterPollutantContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class WaterPollutor {
    public static void postUse(
            World world,
            PlayerEntity user,
            Hand hand,
            TypedActionResult<ItemStack> result
    ) {
        if (!result.getResult().isAccepted()) {
            return;
        }

        ItemStack convertedStack = result.getValue();
        if (convertedStack.contains(TDataComponentTypes.DRINK_PURITY)) {
            BlockHitResult hitResult = ItemAccessor.invokeRaycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
            WaterPollutantContainer pollutants = PollutantLookup.API.find(world, hitResult.getBlockPos(), null);
            pollutants.applyToStack(convertedStack);
        }
    }

    private WaterPollutor() {
        
    }
}