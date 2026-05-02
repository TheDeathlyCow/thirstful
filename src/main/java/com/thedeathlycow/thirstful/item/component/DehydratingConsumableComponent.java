package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

public record DehydratingConsumableComponent(
        boolean alcoholic,
        boolean caffeinated,
        boolean showInTooltip
) implements TooltipProvider {

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
    public static final StreamCodec<ByteBuf, DehydratingConsumableComponent> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            DehydratingConsumableComponent::alcoholic,
            ByteBufCodecs.BOOL,
            DehydratingConsumableComponent::caffeinated,
            ByteBufCodecs.BOOL,
            DehydratingConsumableComponent::showInTooltip,
            DehydratingConsumableComponent::new
    );

    private static final Component ALCOHOLIC = Component.empty()
            .append("Alcohol")
            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED));

    private static final Component CAFFEINATED = Component.empty()
            .append("Caffeinated")
            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED));

    public DehydratingConsumableComponent() {
        this(false, false, true);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
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