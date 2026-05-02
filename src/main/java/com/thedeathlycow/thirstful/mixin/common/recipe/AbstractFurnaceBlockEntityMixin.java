package com.thedeathlycow.thirstful.mixin.common.recipe;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @WrapOperation(
            method = "burn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/Recipe;getResultItem(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack modifyCraftedResult(
            Recipe<? extends RecipeInput> instance,
            HolderLookup.Provider wrapperLookup,
            Operation<ItemStack> original,
            @Local(argsOnly = true) NonNullList<ItemStack> slots
    ) {
        // why the fuck do furnaces not use the craft method? we may never know
        ItemStack result = original.call(instance, wrapperLookup);
        PurificationUtil.pasteurize(new SingleRecipeInput(slots.get(0)), result);
        return result;
    }
}