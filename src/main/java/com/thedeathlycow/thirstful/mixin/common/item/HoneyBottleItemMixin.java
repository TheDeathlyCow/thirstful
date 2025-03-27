package com.thedeathlycow.thirstful.mixin.common.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HoneyBottleItem.class)
public class HoneyBottleItemMixin {
    @WrapOperation(
            method = "finishUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;removeStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z"
            )
    )
    private boolean cancelClearEffectsIfNotClean(
            LivingEntity instance,
            RegistryEntry<StatusEffect> effect,
            Operation<Boolean> original,
            @Local(argsOnly = true) ItemStack stack
    ) {
        PollutantComponent component = stack.get(TDataComponentTypes.POLLUTANTS);
        if (component == null || component.clean(Thirstful.getConfig().common().waterPollution())) {
            original.call(instance, effect);
        }

        return false;
    }
}