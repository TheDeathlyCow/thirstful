package com.thedeathlycow.thirstful.item;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class WaterCollection {
    /**
     * Pollutes water collected by a player from a position. If the player is in creative mode, then the water will be clean.
     *
     * @param stack The watery stack to pollute
     * @param user  The player collecting water
     * @param pos   The position of the water source to collect from
     */
    public static void pollutePlayerCollectedWater(ItemStack stack, Player user, BlockPos pos) {
        if (!user.isCreative()) {
            polluteCollectedWater(stack, user.level(), pos);
        }
    }

    /**
     * Pollutes water collected from a position in the world
     *
     * @param stack     The watery stack to pollute
     * @param world     The world the stack is being collected in
     * @param sourcePos The position of the water sourcePos to collect from
     */
    public static void polluteCollectedWater(ItemStack stack, Level world, BlockPos sourcePos) {
        if (WaterPollution.canCarryPollutants(stack)) {
            PollutantComponent current = stack.getOrDefault(
                    TDataComponentTypes.POLLUTANTS,
                    PollutantComponent.DEFAULT
            );
            PollutantComponent pollutants = WaterPollution.findPollutants(world, sourcePos);
            Objects.requireNonNull(pollutants);
            stack.set(TDataComponentTypes.POLLUTANTS, current.mixWith(pollutants));
        }
    }

    private WaterCollection() {

    }
}