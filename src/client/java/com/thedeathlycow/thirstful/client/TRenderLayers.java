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