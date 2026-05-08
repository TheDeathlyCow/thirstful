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

package com.thedeathlycow.thirstful.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.thedeathlycow.thirstful.block.entity.MeatStillBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MeatStillBlock extends BaseEntityBlock {
    public static final MapCodec<MeatStillBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance ->
                    instance.group(
                            propertiesCodec()
                    ).apply(instance, MeatStillBlock::new)
    );

    public MeatStillBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends MeatStillBlock> codec() {
        return CODEC;
    }

    @Override
    @Nullable
    public MeatStillBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MeatStillBlockEntity(pos, state);
    }
}
