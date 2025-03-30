package com.thedeathlycow.thirstful.mixin.common.block;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.block.PotionCauldronBehavior;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {
    @WrapMethod(
            method = "onUseWithItem"
    )
    private ItemActionResult createPotionCauldron(
            ItemStack inputStack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit,
            Operation<ItemActionResult> original
    ) {
        PollutantComponent pollution = inputStack.getOrDefault(
                TDataComponentTypes.POLLUTANTS,
                PollutantComponent.DEFAULT
        );

        PotionContentsComponent potionContents = inputStack.getOrDefault(
                DataComponentTypes.POTION_CONTENTS,
                PotionCauldronBehavior.WATER_POTION.get()
        );

        ItemActionResult result = original.call(inputStack, state, world, pos, player, hand, hit);
        PotionCauldronBehavior.replaceWaterCauldronWithPotionCauldron(potionContents, pollution, world.getBlockState(pos), world, pos);
        return result;
    }
}