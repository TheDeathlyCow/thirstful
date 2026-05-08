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