package com.thedeathlycow.thirstful.purification;

import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.item.ItemStack;

public class DrinkPurification {

    public static boolean isPureDrink(ItemStack stack) {
        if (!stack.isIn(TItemTags.DRINKS)) {
            return false;
        }

        return !stack.getOrDefault(TDataComponentTypes.IS_DIRTY, true)
                && !stack.getOrDefault(TDataComponentTypes.IS_CONTAMINATED, true);
    }

}