package com.thedeathlycow.thirstful.world;

import com.thedeathlycow.thirstful.item.component.DehydratingConsumableComponent;
import com.thedeathlycow.thirstful.item.component.DrinkPurityComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.item.ItemStack;

public record WaterPollutantContainer(
        boolean contaminated,
        boolean dirty,
        boolean salty
) {
    public void applyToStack(ItemStack stack) {
        DrinkPurityComponent purityComponent = stack.getOrDefault(TDataComponentTypes.DRINK_PURITY, DrinkPurityComponent.DEFAULT);
        stack.set(TDataComponentTypes.DRINK_PURITY, purityComponent.copy(this.dirty, this.contaminated));

        DehydratingConsumableComponent dehydratingConsumableComponent = stack.getOrDefault(TDataComponentTypes.DEHYDRATION_CONSUMABLE, DehydratingConsumableComponent.DEFAULT);
        stack.set(TDataComponentTypes.DEHYDRATION_CONSUMABLE, dehydratingConsumableComponent.withSalty(this.salty));
    }
}