package com.thedeathlycow.thirstful.mixin.common.compat.farmersdelight.present;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.thirst.PurificationUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;

@Mixin(StoveBlockEntity.class)
public class StoveBlockEntityMixin {
    @WrapOperation(
            method = "cookAndOutputItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/crafting/CampfireCookingRecipe;getResult(Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack pasteurizeResult(
            CampfireCookingRecipe instance,
            HolderLookup.Provider wrapperLookup,
            Operation<ItemStack> original,
            @Local(name = "stoveStack") ItemStack input
    ) {
        ItemStack result = original.call(instance, wrapperLookup);
        PurificationUtil.pasteurize(new SingleRecipeInput(input), result);
        return result;
    }
}