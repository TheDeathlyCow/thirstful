package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public record DehydratingConsumableComponent(
        boolean alcoholic,
        boolean caffeinated,
        boolean salty,
        boolean showInTooltip
) implements TooltipAppender {
    public static final Codec<DehydratingConsumableComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL
                                    .optionalFieldOf("contaminated", Boolean.TRUE)
                                    .forGetter(DehydratingConsumableComponent::alcoholic),
                            Codec.BOOL
                                    .optionalFieldOf("contaminated", Boolean.TRUE)
                                    .forGetter(DehydratingConsumableComponent::caffeinated),
                            Codec.BOOL
                                    .optionalFieldOf("dirty", Boolean.TRUE)
                                    .forGetter(DehydratingConsumableComponent::salty),
                            Codec.BOOL
                                    .optionalFieldOf("show_in_tooltip", Boolean.TRUE)
                                    .forGetter(DehydratingConsumableComponent::showInTooltip)
                    )
                    .apply(instance, DehydratingConsumableComponent::new)
    );
    public static final PacketCodec<ByteBuf, DehydratingConsumableComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            DehydratingConsumableComponent::alcoholic,
            PacketCodecs.BOOL,
            DehydratingConsumableComponent::caffeinated,
            PacketCodecs.BOOL,
            DehydratingConsumableComponent::salty,
            PacketCodecs.BOOL,
            DehydratingConsumableComponent::showInTooltip,
            DehydratingConsumableComponent::new
    );

    private static final Text ALCOHOLIC = Text.empty()
            .append("Alcohol")
            .setStyle(Style.EMPTY.withColor(Formatting.RED));

    private static final Text CAFFEINATED = Text.empty()
            .append("Caffeinated")
            .setStyle(Style.EMPTY.withColor(Formatting.RED));

    private static final Text SALTY = Text.empty()
            .append("Salty")
            .setStyle(Style.EMPTY.withColor(Formatting.RED));

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (!this.showInTooltip) {
            return;
        }

        if (this.alcoholic) {
            tooltip.accept(ALCOHOLIC);
        }
        if (this.caffeinated) {
            tooltip.accept(CAFFEINATED);
        }

        if (this.salty) {
            tooltip.accept(SALTY);
        }
    }

    @Contract("->new")
    public DehydratingConsumableComponent distill() {
        return this.copy(false, false, false, this.showInTooltip);
    }

    @Contract("_->new")
    public DehydratingConsumableComponent withShowInTooltip(boolean showInTooltip) {
        return this.copy(this.alcoholic, this.caffeinated, this.salty, showInTooltip);
    }

    @Contract("_,_,_,_->new")
    public DehydratingConsumableComponent copy(
            boolean alcoholic,
            boolean caffeinated,
            boolean salty,
            boolean showInTooltip
    ) {
        return new DehydratingConsumableComponent(alcoholic, caffeinated, salty, showInTooltip);
    }
}