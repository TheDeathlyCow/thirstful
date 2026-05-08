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

package com.thedeathlycow.thirstful.config.client;

import com.thedeathlycow.thirstful.config.OptionName;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;

@IgnoreVisibility
public class ColorConfig extends ConfigSection {
    @OptionName("Contaminated water color")
    @Comment("Sets the color of contaminated water in a cauldron. Mixed with dirty water if also dirty. Note: Press F3+A to reload chunks after changing.")
    private ValidatedColor contaminatedWaterColor = new ValidatedColor(0x5f, 0x91, 0x53);

    @OptionName("Dirty water color")
    @Comment("Sets the color of dirty water in a cauldron. Mixed with contaminated water if also contaminated. Note: Press F3+A to reload chunks after changing.")
    private ValidatedColor dirtyWaterColor = new ValidatedColor(0x91, 0x75, 0x53);

    public int contaminatedWaterColor() {
        return contaminatedWaterColor.get().argb();
    }

    public int dirtyWaterColor() {
        return dirtyWaterColor.get().argb();
    }
}