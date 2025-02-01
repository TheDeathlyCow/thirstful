package com.thedeathlycow.thirstful.item;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import com.thedeathlycow.thirstful.thirst.WaterPollutants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class WaterCollection {
    /**
     * Pollutes water collected by a player from a position. If the player is in creative mode, then the water will be clean.
     *
     * @param stack The watery stack to pollute
     * @param user  The player collecting water
     * @param pos   The position of the water source to collect from
     */
    public static void pollutePlayerCollectedWater(ItemStack stack, PlayerEntity user, BlockPos pos) {
        if (!user.isCreative()) {
            polluteCollectedWater(stack, user.getWorld(), pos);
        }
    }

    /**
     * Pollutes water collected from a position in the world
     *
     * @param stack  The watery stack to pollute
     * @param world  The world the stack is being collected in
     * @param source The position of the water source to collect from
     */
    public static void polluteCollectedWater(ItemStack stack, World world, BlockPos source) {
        if (world.isClient()) {
            return;
        }

        PollutantComponent pollutantComponent = stack.get(TDataComponentTypes.POLLUTANTS);
        if (pollutantComponent != null && stack.isIn(TItemTags.WATERY_DRINKS)) {
            WaterPollutants pollutants = WaterPollutants.lookup(world, source);
            stack.set(
                    TDataComponentTypes.POLLUTANTS,
                    new PollutantComponent(
                            pollutants.dirtiness(),
                            pollutants.diseaseChance(),
                            pollutants.salty(),
                            pollutantComponent.showInTooltip()
                    )
            );
        }
    }

    private WaterCollection() {

    }
}