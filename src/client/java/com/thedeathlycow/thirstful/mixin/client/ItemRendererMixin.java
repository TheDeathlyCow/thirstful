package com.thedeathlycow.thirstful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.thedeathlycow.thirstful.ThirstfulClient;
import com.thedeathlycow.thirstful.client.TRenderLayers;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @WrapOperation(
            method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getFoilBufferDirect(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"
            )
    )
    private VertexConsumer getDirectConsumer(
            MultiBufferSource provider,
            RenderType layer,
            boolean solid,
            boolean glint,
            Operation<VertexConsumer> original,
            ItemStack stack
    ) {
        if (ThirstfulClient.getConfig().enableContaminatedGlint() && !PollutantComponent.get(stack).clean()) {
            return VertexMultiConsumer.create(
                    provider.getBuffer(TRenderLayers.getDirectContaminatedGlint(solid)),
                    provider.getBuffer(layer)
            );
        }

        return original.call(provider, layer, solid, glint);
    }
}