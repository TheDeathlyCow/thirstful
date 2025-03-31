package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.thirst.WaterPollution;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemUsage.class)
public abstract class ItemUsageMixin {
    @WrapMethod(
            method = "exchangeStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/item/ItemStack;"
    )
    private static ItemStack polluteFilledOutput(ItemStack inputStack, PlayerEntity player, ItemStack outputStack, boolean creativeOverride, Operation<ItemStack> original) {
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