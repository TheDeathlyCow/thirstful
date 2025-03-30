package com.thedeathlycow.thirstful.block;

import com.thedeathlycow.thirstful.block.entity.PotionCauldronBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PotionCauldronBlock extends LeveledCauldronBlock implements BlockEntityProvider {
    private final CauldronBehavior.CauldronBehaviorMap fallbackBehavior;

    /**
     * Constructs a leveled cauldron block.
     *
     * @param behaviorMap
     * @param settings
     */
    public PotionCauldronBlock(
            CauldronBehavior.CauldronBehaviorMap behaviorMap,
            CauldronBehavior.CauldronBehaviorMap fallbackBehavior,
            Settings settings
    ) {
        super(Biome.Precipitation.RAIN, behaviorMap, settings);
        this.fallbackBehavior = fallbackBehavior;
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
        Item item = stack.getItem();
        CauldronBehavior behavior = this.getBehaviorMap(item).get(item);

        return behavior.interact(state, world, pos, player, hand, stack);
    }

    @Override
    public Item asItem() {
        return Items.CAULDRON;
    }

    private Map<Item, CauldronBehavior> getBehaviorMap(Item item) {
        return this.behaviorMap.map().containsKey(item)
                ? this.behaviorMap.map()
                : this.fallbackBehavior.map();
    }
}