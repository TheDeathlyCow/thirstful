/*
 * Thirstful: A new approach to thirst and food contamination
 * Copyright (C) 2025 TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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