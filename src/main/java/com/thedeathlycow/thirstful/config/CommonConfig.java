package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Thirstful.MODID + ".common")
public class CommonConfig implements ConfigData {
    int coolingEffectTemperatureChange;

    int warmingEffectTemperatureChange;

    public int getCoolingEffectTemperatureChange() {
        return coolingEffectTemperatureChange;
    }

    public int getWarmingEffectTemperatureChange() {
        return warmingEffectTemperatureChange;
    }
}