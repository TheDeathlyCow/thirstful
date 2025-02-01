package com.thedeathlycow.thirstful.config.common;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".status_effect")
public class StatusEffectConfig implements ConfigData {
    @Comment("Temperature change per tick from the cooling effect per level of cooling")
    int coolingEffectTemperatureChange = -5;

    @Comment("Temperature change per tick from the warming effect per level of warming")
    int warmingEffectTemperatureChange = 5;

    @Comment("Feverish entities have their temperature rise until it reaches this scale")
    float feverMinTemperatureScale = 0.5f;

    @Comment("Temperature rise from the fever effect per level of fever")
    int feverEffectTemperatureChange = 10;

    public int coolingEffectTemperatureChange() {
        return coolingEffectTemperatureChange;
    }

    public int warmingEffectTemperatureChange() {
        return warmingEffectTemperatureChange;
    }

    public float feverMinTemperatureScale() {
        return this.feverMinTemperatureScale;
    }

    public int feverEffectTemperatureChange() {
        return this.feverEffectTemperatureChange;
    }
}