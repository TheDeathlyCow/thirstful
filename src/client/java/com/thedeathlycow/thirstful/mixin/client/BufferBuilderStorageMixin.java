package com.thedeathlycow.thirstful.mixin.client;

import com.thedeathlycow.thirstful.client.TRenderLayers;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.BufferAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilderStorage.class)
public class BufferBuilderStorageMixin {
    @Inject(
            method = "assignBufferBuilder",
            at = @At("RETURN")
    )
    private static void buildRenderLayers(
            Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> map,
            RenderLayer layer,
            CallbackInfo ci
    ) {
        TRenderLayers.buildMap(map);
    }
}