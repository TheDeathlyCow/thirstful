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

public record DehydratingConsumeableComponent(
        boolean alcoholic,
        boolean caffeinated,
        boolean salty,
        boolean showInTooltip
) implements TooltipAppender {
    public static final Codec<DehydratingConsumeableComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL
                                    .optionalFieldOf("contaminated", Boolean.TRUE)
                                    .forGetter(DehydratingConsumeableComponent::alcoholic),
                            Codec.BOOL
                                    .optionalFieldOf("contaminated", Boolean.TRUE)
                                    .forGetter(DehydratingConsumeableComponent::caffeinated),
                            Codec.BOOL
                                    .optionalFieldOf("dirty", Boolean.TRUE)
                                    .forGetter(DehydratingConsumeableComponent::salty),
                            Codec.BOOL
                                    .optionalFieldOf("show_in_tooltip", Boolean.TRUE)
                                    .forGetter(DehydratingConsumeableComponent::showInTooltip)
                    )
                    .apply(instance, DehydratingConsumeableComponent::new)
    );
    public static final PacketCodec<ByteBuf, DehydratingConsumeableComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            DehydratingConsumeableComponent::alcoholic,
            PacketCodecs.BOOL,
            DehydratingConsumeableComponent::caffeinated,
            PacketCodecs.BOOL,
            DehydratingConsumeableComponent::salty,
            PacketCodecs.BOOL,
            DehydratingConsumeableComponent::showInTooltip,
            DehydratingConsumeableComponent::new
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
    public DehydratingConsumeableComponent distill() {
        return this.copy(false, false, false, this.showInTooltip);
    }

    @Contract("_->new")
    public DehydratingConsumeableComponent withShowInTooltip(boolean showInTooltip) {
        return this.copy(this.alcoholic, this.caffeinated, this.salty, showInTooltip);
    }

    @Contract("_,_,_,_->new")
    public DehydratingConsumeableComponent copy(
            boolean alcoholic,
            boolean caffeinated,
            boolean salty,
            boolean showInTooltip
    ) {
        return new DehydratingConsumeableComponent(alcoholic, caffeinated, salty, showInTooltip);
    }
}