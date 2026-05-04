package com.thedeathlycow.thirstful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.thedeathlycow.thirstful.hud.HungerBarContext;
import com.thedeathlycow.thirstful.hud.HungerOverlayRenderEvents;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.SequencedCollection;

@Mixin(Gui.class)
public class GuiPlayerHungerMixin {
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