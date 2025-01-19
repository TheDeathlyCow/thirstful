package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".common")
public class CommonConfig implements ConfigData {
    @Comment("Temperature change per tick from the cooling effect")
    int coolingEffectTemperatureChange = -5;

    @Comment("Temperature change per tick from the warming effect")
    int warmingEffectTemperatureChange = 5;

    public int getCoolingEffectTemperatureChange() {
        return coolingEffectTemperatureChange;
    }

    public int getWarmingEffectTemperatureChange() {
        return warmingEffectTemperatureChange;
    }
}