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

package com.thedeathlycow.thirstful.registry.tag;

import com.thedeathlycow.thirstful.Thirstful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TItemTags {
    public static final TagKey<Item> THIRST_RESTORING_DRINK = key("thirst_restoring_drink");
    public static final TagKey<Item> THIRST_RESTORING_SNACK = key("thirst_restoring_snack");
    public static final TagKey<Item> DIRTY_BY_DEFAULT = key("polluted_consumables/dirty_by_default");
    public static final TagKey<Item> CONTAMINATED_BY_DEFAULT = key("polluted_consumables/contaminated_by_default");
    public static final TagKey<Item> SALTY_BY_DEFAULT = key("polluted_consumables/salty_by_default");
    public static final TagKey<Item> CAN_BE_POLLUTED = key("can_be_polluted");
    public static final TagKey<Item> CAN_NOT_BE_POLLUTED = key("can_not_be_polluted");

    private static TagKey<Item> key(String id) {
        return TagKey.create(Registries.ITEM, Thirstful.id(id));
    }

    private TItemTags() {

    }
}