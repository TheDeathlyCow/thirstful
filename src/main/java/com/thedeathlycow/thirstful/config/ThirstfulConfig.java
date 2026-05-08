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
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;

@IgnoreVisibility
@Version(version = 0)
public class ThirstfulConfig extends Config {
    @OptionName("Status Effect Config")
    @NoComment
    private StatusEffectConfig statusEffect = new StatusEffectConfig();

    @OptionName("Thirst Config")
    @NoComment
    private ThirstConfig thirst = new ThirstConfig();

    @OptionName("Water Pollution Config")
    @NoComment
    private WaterPollutionConfig waterPollution = new WaterPollutionConfig();

    public ThirstfulConfig() {
        super(Thirstful.id("common"));
    }

    public StatusEffectConfig statusEffect() {
        return this.statusEffect;
    }

    public ThirstConfig thirst() {
        return this.thirst;
    }

    public WaterPollutionConfig waterPollution() {
        return this.waterPollution;
    }
}