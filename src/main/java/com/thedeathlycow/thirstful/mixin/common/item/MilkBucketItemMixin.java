package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @WrapOperation(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"
            )
    )
    private boolean cancelClearEffectsIfNotClean(
            LivingEntity instance,
            Operation<Boolean> original,
            @Local(argsOnly = true) ItemStack stack
    ) {
        PollutantComponent component = stack.get(TDataComponentTypes.POLLUTANTS);
        if (component == null || component.clean(Thirstful.getConfig().waterPollution())) {
            original.call(instance);
        }

        return false;
    }
}