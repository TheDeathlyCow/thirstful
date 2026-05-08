/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.thedeathlycow.thirstful.mixin.common.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thedeathlycow.thirstful.effect.AllowStatusEffectCallback;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyReturnValue(
            method = "canBeAffected",
            at = @At("TAIL")
    )
    private boolean checkCanHaveStatusEffects(boolean original, MobEffectInstance effectInstance) {
        if (original) {
            TriState result = AllowStatusEffectCallback.EVENT.invoker().canApplyEffect(
                    (LivingEntity) (Object) this,
                    effectInstance
            );
            if (result != TriState.DEFAULT) {
                return result.get();
            }
        }
        return original;
    }
}