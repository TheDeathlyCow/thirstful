package com.thedeathlycow.thirstful.mixin.common.recipe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractCookingRecipe.class)
public class AbstractCookingRecipeMixin {
    @ModifyReturnValue(
            method = "craft(Lnet/minecraft/recipe/input/SingleStackRecipeInput;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;",
            at = @At("RETURN")
    )
    private ItemStack modifyCookingRemainder(ItemStack original, SingleStackRecipeInput input) {
        PurificationUtil.pasteurize(input, original);
        return original;
    }
}