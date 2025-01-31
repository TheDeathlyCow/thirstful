package com.thedeathlycow.thirstful.item;

import com.thedeathlycow.thirstful.mixin.common.accessor.ItemAccessor;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.thirst.WaterPollutants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class WaterCollection {
    public static void polluteWater(PlayerEntity user, ItemStack output) {
        World world = user.getWorld();
        if (world.isClient() || user.isCreative()) {
            return;
        }
        BlockHitResult hitResult = ItemAccessor.invokeRaycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            polluteStack(output, world, hitResult.getBlockPos());
        }
    }

    public static void polluteStack(ItemStack output, World world, BlockPos source) {
        if (output.contains(TDataComponentTypes.POLLUTANTS)) {
            WaterPollutants pollutants = WaterPollutants.lookup(world, source);
            pollutants.applyToStack(output);
        }
    }

    private WaterCollection() {

    }
}