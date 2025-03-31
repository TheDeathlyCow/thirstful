package com.thedeathlycow.thirstful.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.block.PollutedWaterCauldronBehavior;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {
    @Shadow
    @Final
    protected CauldronBehavior.CauldronBehaviorMap behaviorMap;

    @WrapMethod(
            method = "onUseWithItem"
    )
    private ItemActionResult fillEmptyCauldronOrCancel(
            ItemStack stack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit,
            Operation<ItemActionResult> original
    ) {
        PollutantComponent pollution = stack.getOrDefault(
                TDataComponentTypes.POLLUTANTS,
                PollutantComponent.DEFAULT
        );

        if (pollution.clean()) {
            return original.call(stack, state, world, pos, player, hand, hit);
        }

        if (this.behaviorMap.name().equals(CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.name())) {
            ItemActionResult result = original.call(stack, state, world, pos, player, hand, hit);

            PollutedWaterCauldronBehavior.replaceWithPollutedWaterCauldron(
                    pollution,
                    world.getBlockState(pos),
                    world,
                    pos
            );

            return result;
        }

        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}