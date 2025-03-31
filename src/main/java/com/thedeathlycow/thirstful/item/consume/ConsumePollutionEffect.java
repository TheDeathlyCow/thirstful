package com.thedeathlycow.thirstful.item.consume;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.thedeathlycow.thirstful.registry.TRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

public interface ConsumePollutionEffect {
    Codec<ConsumePollutionEffect> ELEMENT_CODEC = TRegistries.CONSUME_POLLUTION_EFFECT_TYPE.getCodec()
            .dispatch("type", ConsumePollutionEffect::getType, ConsumePollutionEffect.Type::codec);

    PacketCodec<RegistryByteBuf, ConsumePollutionEffect> PACKET_CODEC = PacketCodecs.registryValue(
            TRegistries.CONSUME_POLLUTION_EFFECT_TYPE_KEY
    ).dispatch(ConsumePollutionEffect::getType, ConsumePollutionEffect.Type::packetCodec);

    Type<? extends ConsumePollutionEffect> getType();

    boolean apply(World world, LivingEntity user, ItemStack stack);

    record Type<T extends ConsumePollutionEffect>(
            MapCodec<T> codec,
            PacketCodec<RegistryByteBuf, T> packetCodec
    ) {

    }
}