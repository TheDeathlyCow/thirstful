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

package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;

@IgnoreVisibility
@Version(version = 0)
public class ThirstfulClientConfig extends Config {
    @OptionName("Color Config")
    @NoComment
    private ColorConfig color = new ColorConfig();

    @OptionName("Enable contaminated glint")
    @Comment("Toggle for the green glint on contaminated, dirty, and/or salty items.")
    private boolean enableContaminatedGlint = true;

    public ThirstfulClientConfig() {
        super(Thirstful.id("client"));
    }

    public ColorConfig color() {
        return color;
    }

    public boolean enableContaminatedGlint() {
        return enableContaminatedGlint;
    }
}