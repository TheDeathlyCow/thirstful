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

package com.thedeathlycow.thirstful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.thedeathlycow.thirstful.hud.HungerBarContext;
import com.thedeathlycow.thirstful.hud.HungerOverlayRenderEvents;
import com.thedeathlycow.thirstful.thirst.PlayerThirstComponent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.SequencedCollection;

@Mixin(Gui.class)
public class GuiPlayerHungerMixin {
    @Shadow
    private int tickCount;

    @Shadow
    @Final
    private RandomSource random;

    @Inject(
            method = "renderFood",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;getSaturationLevel()F"
            )
    )
    private void shakeHungerBarWhenDehydrated(
            GuiGraphics guiGraphics,
            Player player,
            int y, int x,
            CallbackInfo ci,
            @Local(ordinal = 4) LocalIntRef localY
    ) {
        if (PlayerThirstComponent.get(player).isDehydrated() && this.tickCount % 3 == 1) {
            localY.set(localY.get() + this.random.nextInt(3) - 1);
        }
    }

    @WrapOperation(
            method = "renderFood",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 0
            )
    )
    private void captureHungerPositions(
            GuiGraphics instance,
            ResourceLocation sprite,
            int x, int y,
            int width, int height,
            Operation<Void> original,
            @Share("positions") LocalRef<SequencedCollection<Vector2i>> positionsRef
    ) {
        original.call(instance, sprite, x, y, width, height);

        if (positionsRef.get() == null) {
            positionsRef.set(new ArrayList<>());
        }

        positionsRef.get().addFirst(new Vector2i(x, y));
    }

    @Inject(
            method = "renderFood",
            at = @At("TAIL")
    )
    private void afterFood(
            GuiGraphics guiGraphics,
            Player player,
            int y, int x,
            CallbackInfo ci,
            @Share("positions") LocalRef<SequencedCollection<Vector2i>> positionsRef
    ) {
        SequencedCollection<Vector2i> positions = positionsRef.get();

        if (positions != null) {
            HungerOverlayRenderEvents.AFTER_HUNGER_BAR.invoker().render(
                    guiGraphics,
                    player,
                    new HungerBarContext(positions)
            );
        }
    }
}