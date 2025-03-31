package com.thedeathlycow.thirstful.mixin.common.recipe;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @WrapOperation(
            method = "craftRecipe",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/recipe/Recipe;getResult(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;"
            )
    )
    private static ItemStack modifyCraftedResult(
            Recipe<? extends RecipeInput> instance,
            RegistryWrapper.WrapperLookup wrapperLookup,
            Operation<ItemStack> original,
            @Local(argsOnly = true) DefaultedList<ItemStack> slots
    ) {
        // why the fuck do furnaces not use the craft method? we may never know
        ItemStack result = original.call(instance, wrapperLookup);
        PurificationUtil.pasteurize(new SingleStackRecipeInput(slots.get(0)), result);
        return result;
    }
}