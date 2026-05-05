package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemUtils.class)
public abstract class ItemUtilsMixin {
    @WrapMethod(
            method = "createFilledResult(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/item/ItemStack;"
    )
    private static ItemStack polluteFilledOutput(ItemStack inputStack, Player player, ItemStack outputStack, boolean creativeOverride, Operation<ItemStack> original) {
        PollutantComponent inputPollution = inputStack.get(TDataComponentTypes.POLLUTANTS);
        if (inputPollution != null && WaterPollution.canCarryPollutants(outputStack)) {
            PollutantComponent existingOutput = outputStack.getOrDefault(
                    TDataComponentTypes.POLLUTANTS,
                    PollutantComponent.DEFAULT
            );
            outputStack.set(TDataComponentTypes.POLLUTANTS, inputPollution.mixWith(existingOutput));
        }

        return original.call(inputStack, player, outputStack, creativeOverride);
    }
}