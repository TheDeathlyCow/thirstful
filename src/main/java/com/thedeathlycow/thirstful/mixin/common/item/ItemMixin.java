package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.item.DrinkTooltipComponent;
import com.thedeathlycow.thirstful.item.component.DrinkComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {
    @ModifyReturnValue(
            method = "getTooltipImage",
            at = @At("RETURN")
    )
    private Optional<TooltipComponent> addWaterTooltipData(
            Optional<TooltipComponent> original,
            @Local(argsOnly = true) ItemStack stack
    ) {
        DrinkComponent drink = DrinkComponent.getByTag(stack);

        if (original.isEmpty() && drink.water() > 0) {
            return Optional.of(new DrinkTooltipComponent(drink));
        }

        return original;
    }
}