package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".common")
public class CommonConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    private final StatusEffectConfig statusEffect = new StatusEffectConfig();

    @ConfigEntry.Gui.CollapsibleObject
    private final ThirstConfig thirstConfig = new ThirstConfig();

    public StatusEffectConfig statusEffect() {
        return this.statusEffect;
    }

    public ThirstConfig thirstConfig() {
        return this.thirstConfig;
    }
}