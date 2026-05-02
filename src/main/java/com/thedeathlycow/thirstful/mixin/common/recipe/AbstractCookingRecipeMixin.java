package com.thedeathlycow.thirstful.mixin.common.recipe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractCookingRecipe.class)
public class AbstractCookingRecipeMixin {
    @ModifyReturnValue(
            method = "assemble(Lnet/minecraft/world/item/crafting/SingleRecipeInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("RETURN")
    )
    private ItemStack modifyCookingRemainder(ItemStack original, SingleRecipeInput input) {
        PurificationUtil.pasteurize(input, original);
        return original;
    }
}