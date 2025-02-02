package com.thedeathlycow.thirstful.mixin.common.compat.farmersdelight.present;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

@Mixin(StoveBlockEntity.class)
public class StoveBlockEntityMixin {
    @WrapOperation(
            method = "cookAndOutputItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/recipe/CampfireCookingRecipe;getResult(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;"
            )
    )
    private ItemStack pasteurizeResult(
            CampfireCookingRecipe instance,
            RegistryWrapper.WrapperLookup wrapperLookup,
            Operation<ItemStack> original,
            @Local(name = "stoveStack") ItemStack input
    ) {
        ItemStack result = original.call(instance, wrapperLookup);
        PurificationUtil.pasteurize(new SingleStackRecipeInput(input), result);
        return result;
    }
}