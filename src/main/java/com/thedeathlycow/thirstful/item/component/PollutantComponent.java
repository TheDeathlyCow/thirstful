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

package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.registry.TDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record PollutantComponent(
        boolean dirty,
        boolean contaminated,
        boolean salty
) implements TooltipProvider {
    public static final PollutantComponent DEFAULT = new PollutantComponent();

    public static final Codec<PollutantComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL
                                    .optionalFieldOf("dirty", DEFAULT.dirty())
                                    .forGetter(PollutantComponent::dirty),
                            Codec.BOOL
                                    .optionalFieldOf("contaminated", DEFAULT.contaminated())
                                    .forGetter(PollutantComponent::contaminated),
                            Codec.BOOL
                                    .optionalFieldOf("salty", DEFAULT.salty())
                                    .forGetter(PollutantComponent::salty)
                    )
                    .apply(instance, PollutantComponent::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, PollutantComponent> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            PollutantComponent::dirty,
            ByteBufCodecs.BOOL,
            PollutantComponent::contaminated,
            ByteBufCodecs.BOOL,
            PollutantComponent::salty,
            PollutantComponent::new
    );

    private static final Component DIRTY_TOOLTIP = Component.empty()
            .append(Component.translatable("item.thirstful.pollutant.dirty"))
            .setStyle(Style.EMPTY.withColor(0x61492d));

    private static final Component CONTAMINATED_TOOLTIP = Component.empty()
            .append(Component.translatable("item.thirstful.pollutant.contaminated"))
            .setStyle(Style.EMPTY.withColor(0x44612d));

    private static final Component SALTY_TOOLTIP = Component.empty()
            .append(Component.translatable("item.thirstful.pollutant.salty"))
            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED));

    private static final Component CLEAN_TOOLTIP = Component.empty()
            .append(Component.translatable("item.thirstful.pollutant.clean"))
            .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA));

    private PollutantComponent() {
        this(false, false, false);
    }

    /**
     * @implNote Called from {@link net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback#EVENT} on client
     */
    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        WaterPollutionConfig config = Thirstful.getConfig().waterPollution();
        if (this.checkedDirty(config)) {
            tooltip.accept(DIRTY_TOOLTIP);
        }
        if (this.checkedContaminated(config)) {
            tooltip.accept(CONTAMINATED_TOOLTIP);
        }
        if (this.checkedSalty(config)) {
            tooltip.accept(SALTY_TOOLTIP);
        }

        if (this.clean(config)) {
            tooltip.accept(CLEAN_TOOLTIP);
        }
    }

    public PollutantComponent mixWith(PollutantComponent other) {
        return new PollutantComponent(
                this.dirty || other.dirty,
                this.contaminated || other.contaminated,
                this.salty || other.salty
        );
    }

    public static PollutantComponent get(ItemStack stack) {
        return stack.getOrDefault(TDataComponentTypes.POLLUTANTS, DEFAULT);
    }

    public boolean checkedDirty(WaterPollutionConfig config) {
        return config.enableDirtiness() && this.dirty;
    }

    public boolean checkedContaminated(WaterPollutionConfig config) {
        return config.enableDisease() && this.contaminated;
    }

    public boolean checkedSalty(WaterPollutionConfig config) {
        return config.enableSaltiness() && this.salty;
    }

    public boolean clean(WaterPollutionConfig config) {
        return !this.checkedDirty(config) && !this.checkedContaminated(config) && !this.checkedSalty(config);
    }

    public boolean clean() {
        return this.clean(Thirstful.getConfig().waterPollution());
    }
}