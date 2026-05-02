package com.thedeathlycow.thirstful.item.consume;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.registry.TConsumePollutionEffects;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record ApplyStatusEffectConsumeEffect(
        MobEffectInstance effect,
        float probability
) implements ConsumePollutionEffect {
    public static final MapCodec<ApplyStatusEffectConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            MobEffectInstance.CODEC
                                    .fieldOf("effect")
                                    .forGetter(ApplyStatusEffectConsumeEffect::effect),
                            Codec.floatRange(0.0f, 1.0f)
                                    .optionalFieldOf("probability", 1.0f)
                                    .forGetter(ApplyStatusEffectConsumeEffect::probability)
                    )
                    .apply(instance, ApplyStatusEffectConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, ApplyStatusEffectConsumeEffect> PACKET_CODEC = StreamCodec.composite(
            MobEffectInstance.STREAM_CODEC,
            ApplyStatusEffectConsumeEffect::effect,
            ByteBufCodecs.FLOAT,
            ApplyStatusEffectConsumeEffect::probability,
            ApplyStatusEffectConsumeEffect::new
    );

    @Override
    public Type<ApplyStatusEffectConsumeEffect> getType() {
        return TConsumePollutionEffects.APPLY_STATUS_EFFECT;
    }

    @Override
    public boolean apply(Level world, LivingEntity user, ItemStack stack) {
        if (user.getRandom().nextFloat() < this.probability) {
            user.addEffect(this.copyEffect());
            return true;
        }

        return false;
    }

    public MobEffectInstance copyEffect() {
        return new MobEffectInstance(this.effect);
    }
}