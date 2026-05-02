package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HoneyBottleItem.class)
public class HoneyBottleItemMixin {
    @WrapOperation(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;removeEffect(Lnet/minecraft/core/Holder;)Z"
            )
    )
    private boolean cancelClearEffectsIfNotClean(
            LivingEntity instance,
            Holder<MobEffect> effect,
            Operation<Boolean> original,
            @Local(argsOnly = true) ItemStack stack
    ) {
        PollutantComponent component = stack.get(TDataComponentTypes.POLLUTANTS);
        if (component == null || component.clean(Thirstful.getConfig().waterPollution())) {
            original.call(instance, effect);
        }

        return false;
    }
}