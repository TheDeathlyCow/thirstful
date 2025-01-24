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

public record PollutantComponent(
        boolean dirty,
        boolean contaminated,
        boolean salty,
        boolean showInTooltip
) implements TooltipAppender {

    public static final PollutantComponent DEFAULT = new PollutantComponent();

    public static final Codec<PollutantComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.BOOL
                                    .optionalFieldOf("dirty", Boolean.FALSE)
                                    .forGetter(PollutantComponent::dirty),
                            Codec.BOOL
                                    .optionalFieldOf("contaminated", Boolean.FALSE)
                                    .forGetter(PollutantComponent::contaminated),
                            Codec.BOOL
                                    .optionalFieldOf("salty", Boolean.FALSE)
                                    .forGetter(PollutantComponent::salty),
                            Codec.BOOL
                                    .optionalFieldOf("show_in_tooltip", Boolean.TRUE)
                                    .forGetter(PollutantComponent::showInTooltip)
                    )
                    .apply(instance, PollutantComponent::new)
    );
    public static final PacketCodec<ByteBuf, PollutantComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            PollutantComponent::dirty,
            PacketCodecs.BOOL,
            PollutantComponent::contaminated,
            PacketCodecs.BOOL,
            PollutantComponent::salty,
            PacketCodecs.BOOL,
            PollutantComponent::showInTooltip,
            PollutantComponent::new
    );

    private static final Text DIRTY_TOOLTIP = Text.empty()
            .append(Text.translatable("item.thirstful.pollutant.dirty"))
            .setStyle(Style.EMPTY.withColor(0x61492d));

    private static final Text CONTAMINATED_TOOLTIP = Text.empty()
            .append(Text.translatable("item.thirstful.pollutant.contaminated"))
            .setStyle(Style.EMPTY.withColor(0x44612d));

    private static final Text SALTY_TOOLTIP = Text.empty()
            .append(Text.translatable("item.thirstful.pollutant.salty"))
            .setStyle(Style.EMPTY.withColor(Formatting.RED));

    private static final Text CLEAN_TOOLTIP = Text.empty()
            .append(Text.translatable("item.thirstful.pollutant.clean"))
            .setStyle(Style.EMPTY.withColor(Formatting.AQUA));

    public PollutantComponent() {
        this(false, false, false, true);
    }

    public PollutantComponent(boolean dirty, boolean contaminated, boolean salty) {
        this(dirty, contaminated, salty, true);
    }

    /**
     * @implNote Called from {@link net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback#EVENT} on client
     */
    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (!this.showInTooltip) {
            return;
        }

        if (this.dirty) {
            tooltip.accept(DIRTY_TOOLTIP);
        }
        if (this.contaminated) {
            tooltip.accept(CONTAMINATED_TOOLTIP);
        }
        if (this.salty) {
            tooltip.accept(SALTY_TOOLTIP);
        }

        if (this.clean()) {
            tooltip.accept(CLEAN_TOOLTIP);
        }
    }

    public boolean clean() {
        return !dirty && !contaminated && !salty;
    }

    @Contract("->new")
    public PollutantComponent boil() {
        return new PollutantComponent(this.dirty, false, this.salty, this.showInTooltip);
    }

    @Contract("->new")
    public PollutantComponent filter() {
        return new PollutantComponent(false, this.contaminated, this.salty, this.showInTooltip);
    }

    @Contract("->new")
    public PollutantComponent distill() {
        return new PollutantComponent(false, false, false, this.showInTooltip);
    }

    @Contract("_->new")
    public PollutantComponent withSalty(boolean salty) {
        return new PollutantComponent(this.dirty, this.contaminated, salty, this.showInTooltip);
    }

    @Contract("_,_->new")
    public PollutantComponent copy(boolean dirty, boolean contaminated, boolean salty) {
        return new PollutantComponent(dirty, contaminated, salty, this.showInTooltip);
    }
}