package com.thedeathlycow.thirstful.mixin.common.block;

import com.thedeathlycow.thirstful.block.PotionCauldronBehavior;
import com.thedeathlycow.thirstful.block.PotionCauldronBlock;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {
    @Inject(
            method = "onUseWithItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void createPotionCauldron(
            ItemStack stack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit,
            CallbackInfoReturnable<ItemActionResult> cir
    ) {
        if (PotionCauldronBehavior.tryPlacePotionCauldron(stack, state, world, pos, player, hand, hit)) {
            cir.setReturnValue(ItemActionResult.success(world.isClient));
        }
    }
}