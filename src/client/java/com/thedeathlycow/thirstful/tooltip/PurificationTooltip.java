package com.thedeathlycow.thirstful.tooltip;

import com.thedeathlycow.thirstful.purification.DrinkPurification;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class PurificationTooltip implements ItemTooltipCallback {
    private static final Text DIRTY = Text.empty()
            .append("Dirty")
            .setStyle(Style.EMPTY.withColor(0x61492d));

    private static final Text CONTAMINATED = Text.empty()
            .append("Contaminated ☣")
            .setStyle(Style.EMPTY.withColor(0x44612d));

    private static final Text PURE = Text.empty()
            .append("Pure")
            .setStyle(Style.EMPTY.withColor(Formatting.AQUA));

    @Override
    public void getTooltip(
            ItemStack stack,
            Item.TooltipContext tooltipContext,
            TooltipType tooltipType,
            List<Text> lines
    ) {
        boolean isDirty = DrinkPurification.isDirty(stack);
        boolean isContaminated = DrinkPurification.isContaminated(stack);

        if (isDirty) {
            addTooltipBeforeAdvanced(stack, tooltipType, lines, DIRTY);
        }

        if (isContaminated) {
            addTooltipBeforeAdvanced(stack, tooltipType, lines, CONTAMINATED);
        }

        if (!isDirty && !isContaminated) {
            addTooltipBeforeAdvanced(stack, tooltipType, lines, PURE);
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