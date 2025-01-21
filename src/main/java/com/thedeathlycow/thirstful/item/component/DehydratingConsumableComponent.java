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
        boolean showInTooltip
) implements TooltipAppender {

    public static final DehydratingConsumableComponent DEFAULT = new DehydratingConsumableComponent();

    public static final Codec<DehydratingConsumableComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL
                                    .optionalFieldOf("alcoholic", Boolean.FALSE)
                                    .forGetter(DehydratingConsumableComponent::alcoholic),
                            Codec.BOOL
                                    .optionalFieldOf("caffeinated", Boolean.FALSE)
                                    .forGetter(DehydratingConsumableComponent::caffeinated),
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
            DehydratingConsumableComponent::showInTooltip,
            DehydratingConsumableComponent::new
    );

    private static final Text ALCOHOLIC = Text.empty()
            .append("Alcohol")
            .setStyle(Style.EMPTY.withColor(Formatting.RED));

    private static final Text CAFFEINATED = Text.empty()
            .append("Caffeinated")
            .setStyle(Style.EMPTY.withColor(Formatting.RED));

    public DehydratingConsumableComponent() {
        this(false, false, true);
    }

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
    }

    @Contract("->new")
    public DehydratingConsumableComponent distill() {
        return new DehydratingConsumableComponent(false, false, this.showInTooltip);
    }

    @Contract("_,_->new")
    public DehydratingConsumableComponent copy(
            boolean alcoholic,
            boolean caffeinated
    ) {
        return new DehydratingConsumableComponent(alcoholic, caffeinated, this.showInTooltip);
    }
}