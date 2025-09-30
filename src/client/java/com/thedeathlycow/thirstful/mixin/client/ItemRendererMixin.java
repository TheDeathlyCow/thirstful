package com.thedeathlycow.thirstful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.ThirstfulClient;
import com.thedeathlycow.thirstful.client.TRenderLayers;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @WrapOperation(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"
            )
    )
    private VertexConsumer getDirectConsumer(
            VertexConsumerProvider provider,
            RenderLayer layer,
            boolean solid,
            boolean glint,
            Operation<VertexConsumer> original,
            ItemStack stack
    ) {
        if (ThirstfulClient.getConfig().enableContaminatedGlint() && !PollutantComponent.get(stack).clean()) {
            return VertexConsumers.union(
                    provider.getBuffer(TRenderLayers.getDirectContaminatedGlint(solid)),
                    provider.getBuffer(layer)
            );
        }

        return original.call(provider, layer, solid, glint);
    }
}