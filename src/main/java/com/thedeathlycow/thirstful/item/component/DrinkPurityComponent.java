package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.function.Consumer;

public record DrinkPurityComponent(
        boolean isDirty,
        boolean isContaminated,
        boolean showInTooltip
) implements TooltipAppender {

    public static final Codec<DrinkPurityComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL
                                    .optionalFieldOf("is_dirty", Boolean.TRUE)
                                    .forGetter(DrinkPurityComponent::isDirty),
                            Codec.BOOL
                                    .optionalFieldOf("is_contaminated", Boolean.TRUE)
                                    .forGetter(DrinkPurityComponent::isContaminated),
                            Codec.BOOL
                                    .optionalFieldOf("show_in_tooltip", Boolean.TRUE)
                                    .forGetter(DrinkPurityComponent::showInTooltip)
                    )
                    .apply(instance, DrinkPurityComponent::new)
    );
    public static final PacketCodec<ByteBuf, DrinkPurityComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            DrinkPurityComponent::isDirty,
            PacketCodecs.BOOL,
            DrinkPurityComponent::isContaminated,
            PacketCodecs.BOOL,
            DrinkPurityComponent::showInTooltip,
            DrinkPurityComponent::new
    );

    private static final Text DIRTY = Text.empty()
            .append("Dirty")
            .setStyle(Style.EMPTY.withColor(0x61492d));

    private static final Text CONTAMINATED = Text.empty()
            .append("Contaminated")
            .setStyle(Style.EMPTY.withColor(0x44612d));

    private static final Text PURE = Text.empty()
            .append("Pure")
            .setStyle(Style.EMPTY.withColor(Formatting.AQUA));

    public DrinkPurityComponent() {
        this(true, true, true);
    }

    public DrinkPurityComponent(boolean isDirty, boolean isContaminated) {
        this(isDirty, isContaminated, true);
    }

    /**
     * @implNote Called from {@link net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback#EVENT} on client
     */
    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (!this.showInTooltip) {
            return;
        }

        if (this.isDirty) {
            tooltip.accept(DIRTY);
        }
        if (this.isContaminated) {
            tooltip.accept(CONTAMINATED);
        }

        if (this.isPure()) {
            tooltip.accept(PURE);
        }
    }

    public boolean isPure() {
        return !isDirty && !isContaminated;
    }

    @Contract("->new")
    public DrinkPurityComponent boil() {
        return this.copy(this.isDirty, false, this.showInTooltip);
    }

    @Contract("->new")
    public DrinkPurityComponent filter() {
        return this.copy(false, this.isContaminated, this.showInTooltip);
    }

    @Contract("->new")
    public DrinkPurityComponent distill() {
        return this.copy(false, false, this.showInTooltip);
    }

    @Contract("_->new")
    public DrinkPurityComponent withShowInTooltip(boolean showInTooltip) {
        return this.copy(this.isDirty, this.isContaminated, showInTooltip);
    }

    @Contract("_,_,_->new")
    public DrinkPurityComponent copy(
            boolean isDirty,
            boolean isContaminated,
            boolean showInTooltip
    ) {
        return new DrinkPurityComponent(isDirty, isContaminated, showInTooltip);
    }
}