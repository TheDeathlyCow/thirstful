package com.thedeathlycow.thirstful.item;

import com.thedeathlycow.thirstful.item.component.DrinkComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record DrinkTooltipComponent(
        DrinkComponent component
) implements TooltipComponent {
}