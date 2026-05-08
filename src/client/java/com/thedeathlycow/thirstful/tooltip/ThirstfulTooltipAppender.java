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

package com.thedeathlycow.thirstful.tooltip;

import com.thedeathlycow.thirstful.item.component.DrinkComponent;
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
        this.appendTooltip(DrinkComponent.getByTag(stack, null), tooltipContext, tooltipType, builder);
    }

    private <T extends TooltipProvider> void appendTooltip(
            ItemStack stack,
            DataComponentType<T> componentType,
            Item.TooltipContext context,
            TooltipFlag type,
            Consumer<Component> textConsumer
    ) {
        this.appendTooltip(stack.get(componentType), context, type, textConsumer);
    }

    private <T extends TooltipProvider> void appendTooltip(
            T tooltipAppender,
            Item.TooltipContext context,
            TooltipFlag type,
            Consumer<Component> textConsumer
    ) {
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