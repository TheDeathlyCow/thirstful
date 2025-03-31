package com.thedeathlycow.thirstful.item.consume;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.registry.TConsumePollutionEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

public record ApplyStatusEffectConsumeEffect(
        StatusEffectInstance effect,
        float probability
) implements ConsumePollutionEffect {
    public static final MapCodec<ApplyStatusEffectConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            StatusEffectInstance.CODEC
                                    .fieldOf("effect")
                                    .forGetter(ApplyStatusEffectConsumeEffect::effect),
                            Codec.floatRange(0.0f, 1.0f)
                                    .optionalFieldOf("probability", 1.0f)
                                    .forGetter(ApplyStatusEffectConsumeEffect::probability)
                    )
                    .apply(instance, ApplyStatusEffectConsumeEffect::new)
    );
    public static final PacketCodec<RegistryByteBuf, ApplyStatusEffectConsumeEffect> PACKET_CODEC = PacketCodec.tuple(
            StatusEffectInstance.PACKET_CODEC,
            ApplyStatusEffectConsumeEffect::effect,
            PacketCodecs.FLOAT,
            ApplyStatusEffectConsumeEffect::probability,
            ApplyStatusEffectConsumeEffect::new
    );

    @Override
    public Type<ApplyStatusEffectConsumeEffect> getType() {
        return TConsumePollutionEffects.APPLY_STATUS_EFFECT;
    }

    @Override
    public boolean apply(World world, LivingEntity user, ItemStack stack) {
        if (user.getRandom().nextFloat() < this.probability) {
            user.addStatusEffect(this.copyEffect());
            return true;
        }

        return false;
    }

    public StatusEffectInstance copyEffect() {
        return new StatusEffectInstance(this.effect);
    }
}