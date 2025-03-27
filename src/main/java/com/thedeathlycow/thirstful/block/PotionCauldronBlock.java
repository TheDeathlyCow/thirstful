package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.block.entity.PotionCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class PotionCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PotionCauldronBlock(
            CauldronBehavior.CauldronBehaviorMap behaviorMap,
            Settings settings
    ) {
        super(Biome.Precipitation.RAIN, behaviorMap, settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PotionCauldronBlockEntity(pos, state);
    }

    @Override
    protected ItemActionResult onUseWithItem(
            ItemStack stack,
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            Hand hand,
            BlockHitResult hit
    ) {
        if (stack.contains(DataComponentTypes.POTION_CONTENTS)) {
            return ItemActionResult.FAIL;
        } else {
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        }
    }
}