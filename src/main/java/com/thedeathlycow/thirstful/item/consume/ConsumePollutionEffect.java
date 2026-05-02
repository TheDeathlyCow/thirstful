package com.thedeathlycow.thirstful.item.consume;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.thedeathlycow.thirstful.registry.TRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ConsumePollutionEffect {
    Codec<ConsumePollutionEffect> ELEMENT_CODEC = TRegistries.CONSUME_POLLUTION_EFFECT_TYPE.byNameCodec()
            .dispatch("type", ConsumePollutionEffect::getType, ConsumePollutionEffect.Type::codec);

    StreamCodec<RegistryFriendlyByteBuf, ConsumePollutionEffect> PACKET_CODEC = ByteBufCodecs.registry(
            TRegistries.CONSUME_POLLUTION_EFFECT_TYPE_KEY
    ).dispatch(ConsumePollutionEffect::getType, ConsumePollutionEffect.Type::packetCodec);

    Type<? extends ConsumePollutionEffect> getType();

    boolean apply(Level world, LivingEntity user, ItemStack stack);

    record Type<T extends ConsumePollutionEffect>(
            MapCodec<T> codec,
            StreamCodec<RegistryFriendlyByteBuf, T> packetCodec
    ) {

    }
}