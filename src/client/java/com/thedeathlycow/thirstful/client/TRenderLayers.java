package com.thedeathlycow.thirstful.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;

public final class TRenderLayers {
    private static final RenderLayer CONTAMINATED_GLINT = RenderLayer.of(
            "glint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            RenderLayer.DEFAULT_BUFFER_SIZE,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.GLINT_PROGRAM)
                    .texture(new RenderPhase.Texture(ItemRenderer.ITEM_ENCHANTMENT_GLINT, true, false))
                    .writeMaskState(RenderPhase.COLOR_MASK)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                    .transparency(RenderPhase.GLINT_TRANSPARENCY)
                    .texturing(RenderPhase.GLINT_TEXTURING)
                    .build(false)
    );

    private TRenderLayers() {

    }
}