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

package com.thedeathlycow.thirstful.config.common;

import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;

@IgnoreVisibility
public class WaterPollutionConfig extends ConfigSection {
    @OptionName("Enable disease")
    @NoComment
    private boolean enableDisease = true;

    @OptionName("Enable dirtiness")
    @NoComment
    private boolean enableDirtiness = true;

    @OptionName("Enable saltiness")
    @NoComment
    private boolean enableSaltiness = true;

    public boolean enableDisease() {
        return enableDisease;
    }

    public boolean enableDirtiness() {
        return enableDirtiness;
    }

    public boolean enableSaltiness() {
        return enableSaltiness;
    }
}