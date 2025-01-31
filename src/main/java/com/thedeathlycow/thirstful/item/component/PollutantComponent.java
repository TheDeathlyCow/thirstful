package com.thedeathlycow.thirstful.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.Consumer;

public record PollutantComponent(
        List<StatusEffectEntry> dirtinessEffects,
        List<StatusEffectEntry> diseaseEffects,
        boolean salty,
        boolean showInTooltip
) implements TooltipAppender {
    public static final PollutantComponent DEFAULT = new PollutantComponent();

    public static final Codec<PollutantComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.list(StatusEffectEntry.CODEC)
                                    .lenientOptionalFieldOf("dirtiness", List.of())
                                    .forGetter(PollutantComponent::dirtinessEffects),
                            Codec.list(StatusEffectEntry.CODEC)
                                    .lenientOptionalFieldOf("disease_chance", List.of())
                                    .forGetter(PollutantComponent::diseaseEffects),
                            Codec.BOOL
                                    .optionalFieldOf("salty", Boolean.FALSE)
                                    .forGetter(PollutantComponent::salty),
                            Codec.BOOL
                                    .optionalFieldOf("show_in_tooltip", Boolean.TRUE)
                                    .forGetter(PollutantComponent::showInTooltip)
                    )
                    .apply(instance, PollutantComponent::new)
    );
    public static final PacketCodec<RegistryByteBuf, PollutantComponent> PACKET_CODEC = PacketCodec.tuple(
            StatusEffectEntry.PACKET_CODEC.collect(PacketCodecs.toList()),
            PollutantComponent::dirtinessEffects,
            StatusEffectEntry.PACKET_CODEC.collect(PacketCodecs.toList()),
            PollutantComponent::diseaseEffects,
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

    private static final int LONG_EFFECT_TIME = 60 * 20;
    private static final int SHORT_EFFECT_TIME = 10 * 20;

    public PollutantComponent() {
        this(List.of(), List.of(), false, true);
    }

    /**
     * Creates a component using the default effects of hunger, poison, and fever using a custom chance
     *
     * @param dirtiness     Chance for dirtiness effects
     * @param diseaseChance Chance for disease effects
     * @param salty         Saltiness flag
     */
    public PollutantComponent(float dirtiness, float diseaseChance, boolean salty) {
        this(defaultDirtinessEffects(dirtiness), defaultDiseaseEffects(diseaseChance), salty, true);
    }

    /**
     * @implNote Called from {@link net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback#EVENT} on client
     */
    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (!this.showInTooltip()) {
            return;
        }

        WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();
        if (this.dirty(config)) {
            tooltip.accept(DIRTY_TOOLTIP);
        }
        if (this.contaminated(config)) {
            tooltip.accept(CONTAMINATED_TOOLTIP);
        }
        if (this.salty(config)) {
            tooltip.accept(SALTY_TOOLTIP);
        }

        if (this.clean(config)) {
            tooltip.accept(CLEAN_TOOLTIP);
        }
    }

    public boolean dirty(WaterPollutionConfig config) {
        return config.enableDirtiness() && !this.dirtinessEffects.isEmpty();
    }

    public boolean contaminated(WaterPollutionConfig config) {
        return config.enableDisease() && !this.diseaseEffects.isEmpty();
    }

    public boolean salty(WaterPollutionConfig config) {
        return config.enableSaltiness() && this.salty;
    }

    public boolean clean(WaterPollutionConfig config) {
        return !this.dirty(config) && !this.contaminated(config) && !this.salty(config);
    }

    public void applyEffects(LivingEntity entity) {
        WaterPollutionConfig config = Thirstful.getConfig().common().waterPollution();
        if (this.dirty(config)) {
            this.applyEffects(entity, this.dirtinessEffects());
        }
        if (this.contaminated(config)) {
            this.applyEffects(entity, this.diseaseEffects());
        }
    }

    private void applyEffects(LivingEntity entity, List<StatusEffectEntry> effects) {
        for (StatusEffectEntry effect : effects) {
            entity.addStatusEffect(effect.effect());
        }
    }

    public record StatusEffectEntry(StatusEffectInstance effect, float probability) {
        public static final Codec<StatusEffectEntry> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                StatusEffectInstance.CODEC
                                        .fieldOf("effect")
                                        .forGetter(StatusEffectEntry::effect),
                                Codec.floatRange(0.0f, 1.0f)
                                        .optionalFieldOf("probability", 1.0f)
                                        .forGetter(StatusEffectEntry::probability)
                        )
                        .apply(instance, StatusEffectEntry::new)
        );
        public static final PacketCodec<RegistryByteBuf, StatusEffectEntry> PACKET_CODEC = PacketCodec.tuple(
                StatusEffectInstance.PACKET_CODEC,
                StatusEffectEntry::effect,
                PacketCodecs.FLOAT,
                StatusEffectEntry::probability,
                StatusEffectEntry::new
        );

        @Override
        public StatusEffectInstance effect() {
            return new StatusEffectInstance(this.effect);
        }
    }

    private static List<StatusEffectEntry> defaultDirtinessEffects(float chance) {
        if (chance > 0f) {
            return List.of(
                    new StatusEffectEntry(
                            new StatusEffectInstance(StatusEffects.HUNGER, LONG_EFFECT_TIME),
                            chance
                    )
            );
        }
        return List.of();
    }

    private static List<StatusEffectEntry> defaultDiseaseEffects(float chance) {
        if (chance > 0f) {
            return List.of(
                    new StatusEffectEntry(
                            new StatusEffectInstance(StatusEffects.POISON, SHORT_EFFECT_TIME),
                            chance
                    ),
                    new StatusEffectEntry(
                            new StatusEffectInstance(TStatusEffects.FEVER, LONG_EFFECT_TIME),
                            chance
                    )
            );
        }
        return List.of();
    }
}