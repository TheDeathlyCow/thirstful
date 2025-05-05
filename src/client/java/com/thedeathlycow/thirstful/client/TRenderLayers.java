package com.thedeathlycow.thirstful.client;

import com.thedeathlycow.thirstful.Thirstful;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.util.Identifier;

import java.util.Map;

public final class TRenderLayers {
    private static final Identifier ITEM_CONTAMINATED_GLINT = Thirstful.id("textures/misc/contaminated_item_glint.png");

    private static final RenderLayer CONTAMINATED_GLINT = RenderLayer.of(
            "thirstful_glint",
            VertexFormats.POSITION_TEXTURE,
            VertexFormat.DrawMode.QUADS,
            RenderLayer.DEFAULT_BUFFER_SIZE,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.GLINT_PROGRAM)
                    .texture(new RenderPhase.Texture(ITEM_CONTAMINATED_GLINT, true, false))
                    .writeMaskState(RenderPhase.COLOR_MASK)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .depthTest(RenderPhase.EQUAL_DEPTH_TEST)
                    .transparency(RenderPhase.GLINT_TRANSPARENCY)
                    .texturing(RenderPhase.GLINT_TEXTURING)
                    .build(false)
    );

    public static RenderLayer getDirectContaminatedGlint(boolean solid) {
        return solid ? CONTAMINATED_GLINT : RenderLayer.getDirectEntityGlint();
    }

    public static RenderLayer getContaminatedGlint(boolean solid) {
        return solid ? CONTAMINATED_GLINT : RenderLayer.getEntityGlint();
    }

    public static void buildMap(Map<RenderLayer, BufferAllocator> map) {
        assignToAllocator(map, CONTAMINATED_GLINT);
    }

    private static void assignToAllocator(Map<RenderLayer, BufferAllocator> map, RenderLayer layer) {
        map.put(layer, new BufferAllocator(layer.getExpectedBufferSize()));
    }

    private TRenderLayers() {

    }
}