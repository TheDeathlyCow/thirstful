package com.thedeathlycow.thirstful.tooltip;

import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import java.util.List;
import java.util.function.Consumer;

public class ThirstfulTooltipAppender implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipFlag tooltipType, List<Component> lines) {
        Consumer<Component> builder = text -> addTooltipBeforeAdvanced(stack, tooltipType, lines, text);

        this.appendTooltip(stack, TDataComponentTypes.POLLUTANTS, tooltipContext, tooltipType, builder);
        this.appendTooltip(stack, TDataComponentTypes.DEHYDRATION_CONSUMABLE, tooltipContext, tooltipType, builder);
    }

    private <T extends TooltipProvider> void appendTooltip(
            ItemStack stack,
            DataComponentType<T> componentType,
            Item.TooltipContext context,
            TooltipFlag type,
            Consumer<Component> textConsumer
    ) {
        T tooltipAppender = stack.get(componentType);
        if (tooltipAppender != null) {
            tooltipAppender.addToTooltip(context, textConsumer, type);
        }
    }

    private static void addTooltipBeforeAdvanced(
            ItemStack stack,
            TooltipFlag tooltipType,
            List<Component> lines,
            Component tooltipText
    ) {
        if (!tooltipType.isAdvanced()) {
            lines.add(tooltipText);
            return;
        }

        ResourceLocation identifier = BuiltInRegistries.ITEM.getKey(stack.getItem());
        Component idAsText = Component.literal(identifier.toString());

        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).contains(idAsText)) {
                lines.add(i, tooltipText);
                return;
            }
        }
    }
}