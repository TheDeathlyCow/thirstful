package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Thirstful.MODID + ".common")
public class CommonConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    StatusEffectConfig statusEffect = new StatusEffectConfig();

    @ConfigEntry.Gui.CollapsibleObject
    ThirstConfig thirst = new ThirstConfig();

    @ConfigEntry.Gui.CollapsibleObject
    WaterPollutionConfig waterPollution = new WaterPollutionConfig();

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