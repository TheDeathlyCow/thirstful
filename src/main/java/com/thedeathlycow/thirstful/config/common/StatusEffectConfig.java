package com.thedeathlycow.thirstful.config.common;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".status_effect")
public class StatusEffectConfig implements ConfigData {
    @Comment("Temperature change per tick from the cooling effect")
    int coolingEffectTemperatureChange = -5;

    @Comment("Temperature change per tick from the warming effect")
    int warmingEffectTemperatureChange = 5;

    public int coolingEffectTemperatureChange() {
        return coolingEffectTemperatureChange;
    }

    public int warmingEffectTemperatureChange() {
        return warmingEffectTemperatureChange;
    }
}