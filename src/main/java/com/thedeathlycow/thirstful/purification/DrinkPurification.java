package com.thedeathlycow.thirstful.purification;

import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import com.thedeathlycow.thirstful.registry.tag.TItemTags;
import net.minecraft.item.ItemStack;

public final class DrinkPurification {
    public static boolean isDirty(ItemStack stack) {
        return stack.getOrDefault(TDataComponentTypes.IS_DIRTY, stack.isIn(TItemTags.DRINKS));
    }

    public static boolean isContaminated(ItemStack stack) {
        return stack.getOrDefault(TDataComponentTypes.IS_CONTAMINATED, stack.isIn(TItemTags.DRINKS));
    }

    public static boolean isPureDrink(ItemStack stack) {
        if (!stack.isIn(TItemTags.DRINKS)) {
            return false;
        }

        return !stack.getOrDefault(TDataComponentTypes.IS_DIRTY, true)
                && !stack.getOrDefault(TDataComponentTypes.IS_CONTAMINATED, true);
    }

    private DrinkPurification() {

    }
}