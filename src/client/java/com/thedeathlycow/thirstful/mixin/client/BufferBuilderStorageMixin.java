package com.thedeathlycow.thirstful.mixin.client;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.thedeathlycow.thirstful.client.TRenderLayers;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public class BufferBuilderStorageMixin {
    @Inject(
            method = "put",
            at = @At("RETURN")
    )
    private static void buildRenderLayers(
            Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map,
            RenderType layer,
            CallbackInfo ci
    ) {
        TRenderLayers.buildMap(map);
    }
}