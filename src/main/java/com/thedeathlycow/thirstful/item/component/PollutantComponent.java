package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public record PollutantComponent(
        boolean dirty,
        boolean contaminated,
        boolean salty
) implements TooltipAppender {
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
    public static final PacketCodec<RegistryByteBuf, PollutantComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL,
            PollutantComponent::dirty,
            PacketCodecs.BOOL,
            PollutantComponent::contaminated,
            PacketCodecs.BOOL,
            PollutantComponent::salty,
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

    private PollutantComponent() {
        this(false, false, false);
    }

    /**
     * @implNote Called from {@link net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback#EVENT} on client
     */
    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();
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
        return this.clean(Thirstful.getConfig().common().waterPollution());
    }
}