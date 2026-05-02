package com.thedeathlycow.thirstful.client;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.thedeathlycow.thirstful.Thirstful;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public final class TRenderLayers {
    private static final ResourceLocation ITEM_CONTAMINATED_GLINT = Thirstful.id("textures/misc/contaminated_item_glint.png");

    private static final RenderType CONTAMINATED_GLINT = RenderType.create(
            "thirstful_glint",
            DefaultVertexFormat.POSITION_TEX,
            VertexFormat.Mode.QUADS,
            RenderType.TRANSIENT_BUFFER_SIZE,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_GLINT_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(ITEM_CONTAMINATED_GLINT, true, false))
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                    .setTransparencyState(RenderStateShard.GLINT_TRANSPARENCY)
                    .setTexturingState(RenderStateShard.GLINT_TEXTURING)
                    .createCompositeState(false)
    );

    public static RenderType getDirectContaminatedGlint(boolean solid) {
        return solid ? CONTAMINATED_GLINT : RenderType.entityGlintDirect();
    }

    public static RenderType getContaminatedGlint(boolean solid) {
        return solid ? CONTAMINATED_GLINT : RenderType.entityGlint();
    }

    public static void buildMap(Map<RenderType, ByteBufferBuilder> map) {
        assignToAllocator(map, CONTAMINATED_GLINT);
    }

    private static void assignToAllocator(Map<RenderType, ByteBufferBuilder> map, RenderType layer) {
        map.put(layer, new ByteBufferBuilder(layer.bufferSize()));
    }

    private TRenderLayers() {

    }
}