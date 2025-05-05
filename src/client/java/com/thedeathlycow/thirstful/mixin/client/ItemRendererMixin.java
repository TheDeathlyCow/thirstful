package com.thedeathlycow.thirstful.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.thedeathlycow.thirstful.item.component.PollutantComponent;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
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
    private VertexConsumer getDirectContaminatedGlintConsumer(
            VertexConsumerProvider vertexConsumers,
            RenderLayer layer,
            boolean solid,
            boolean glint,
            Operation<VertexConsumer> original,
            ItemStack stack
    ) {
        PollutantComponent pollutants = stack.get(TDataComponentTypes.POLLUTANTS);
        if (pollutants != null && !pollutants.clean()) {
            return VertexConsumers.union(
                    vertexConsumers.getBuffer(solid ? RenderLayer.getGlint() : RenderLayer.getDirectEntityGlint()),
                    vertexConsumers.getBuffer(layer)
            );
        }
        return original.call(vertexConsumers, layer, solid, glint);
    }

    @WrapOperation(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;")
    )
    private VertexConsumer getContaminatedGlintConsumer(
            VertexConsumerProvider vertexConsumers,
            RenderLayer layer,
            boolean solid,
            boolean glint,
            Operation<VertexConsumer> original,
            ItemStack stack
    ) {
        PollutantComponent pollutants = stack.get(TDataComponentTypes.POLLUTANTS);
        if (pollutants != null && !pollutants.clean()) {
            return VertexConsumers.union(
                    vertexConsumers.getBuffer(solid ? RenderLayer.getGlint() : RenderLayer.getEntityGlint()),
                    vertexConsumers.getBuffer(layer)
            );
        }
        return original.call(vertexConsumers, layer, solid, glint);
    }
}