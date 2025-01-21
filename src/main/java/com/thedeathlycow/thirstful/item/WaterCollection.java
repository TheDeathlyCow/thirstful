package com.thedeathlycow.thirstful.item;

import com.thedeathlycow.thirstful.mixin.common.accessor.ItemAccessor;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.world.WaterPollutants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class WaterCollection {
    public static void polluteWater(
            PlayerEntity user,
            ItemStack input,
            ItemStack output
    ) {
        World world = user.getWorld();
        if (world.isClient() || user.isCreative()) {
            return;
        }

        if (output.contains(TDataComponentTypes.POLLUTANTS)) {
            BlockHitResult hitResult = ItemAccessor.invokeRaycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                WaterPollutants pollutants = WaterPollutants.LOOKUP.find(world, hitResult.getBlockPos(), null);
                pollutants.applyToStack(output);
            }
        }
    }

    private WaterCollection() {

    }
}