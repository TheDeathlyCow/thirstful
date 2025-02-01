package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public final class PurificationUtil {

    public static void pasteurize(ItemStack stack) {
        PollutantComponent component = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);

        component = new PollutantComponent(
                List.copyOf(component.dirtinessEffects()),
                Collections.emptyList(),
                component.salty(),
                component.showInTooltip()
        );

        stack.set(TDataComponentTypes.POLLUTANTS, component);
    }

    public static void filter(ItemStack stack) {
        PollutantComponent component = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);

        component = new PollutantComponent(
                Collections.emptyList(),
                List.copyOf(component.diseaseEffects()),
                component.salty(),
                component.showInTooltip()
        );

        stack.set(TDataComponentTypes.POLLUTANTS, component);
    }

    public static void distill(ItemStack stack) {
        PollutantComponent component = stack.getOrDefault(TDataComponentTypes.POLLUTANTS, PollutantComponent.DEFAULT);

        component = new PollutantComponent(
                Collections.emptyList(),
                Collections.emptyList(),
                false,
                component.showInTooltip()
        );
        stack.set(TDataComponentTypes.POLLUTANTS, component);
    }

    private PurificationUtil() {

    }
}