package com.thedeathlycow.thirstful.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.block.entity.PotionCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PotionCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
    private final CauldronBehavior.CauldronBehaviorMap behaviorMap;

    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PotionCauldronBlock(
            CauldronBehavior.CauldronBehaviorMap behaviorMap,
            CauldronBehavior.CauldronBehaviorMap fallbackBehaviorMap,
            Settings settings
    ) {
        super(Biome.Precipitation.RAIN, fallbackBehaviorMap, settings);
        this.behaviorMap = behaviorMap;
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
        Map<Item, CauldronBehavior> behaviours = this.behaviorMap.map();
        Item item = stack.getItem();

        if (behaviours.containsKey(item)) {
            return behaviours.get(item).interact(state, world, pos, player, hand, stack);
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}