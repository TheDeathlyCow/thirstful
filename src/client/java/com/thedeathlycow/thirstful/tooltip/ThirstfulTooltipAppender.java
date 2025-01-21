package com.thedeathlycow.thirstful.tooltip;

import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ThirstfulTooltipAppender implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> lines) {
        Consumer<Text> builder = text -> addTooltipBeforeAdvanced(stack, tooltipType, lines, text);

        this.appendTooltip(stack, TDataComponentTypes.POLLUTANTS, tooltipContext, tooltipType, builder);
        this.appendTooltip(stack, TDataComponentTypes.DEHYDRATION_CONSUMABLE, tooltipContext, tooltipType, builder);
    }

    private <T extends TooltipAppender> void appendTooltip(
            ItemStack stack,
            ComponentType<T> componentType,
            Item.TooltipContext context,
            TooltipType type,
            Consumer<Text> textConsumer
    ) {
        T tooltipAppender = stack.get(componentType);
        if (tooltipAppender != null) {
            tooltipAppender.appendTooltip(context, textConsumer, type);
        }
    }

    private static void addTooltipBeforeAdvanced(
            ItemStack stack,
            TooltipType tooltipType,
            List<Text> lines,
            Text tooltipText
    ) {
        if (!tooltipType.isAdvanced()) {
            lines.add(tooltipText);
            return;
        }

        Identifier identifier = Registries.ITEM.getId(stack.getItem());
        Text idAsText = Text.literal(identifier.toString());

        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).contains(idAsText)) {
                lines.add(i, tooltipText);
                return;
            }
        }
    }
}