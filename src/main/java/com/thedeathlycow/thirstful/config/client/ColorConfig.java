package com.thedeathlycow.thirstful.config.client;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".color")
public class ColorConfig implements ConfigData {
    @ConfigEntry.ColorPicker
    @Comment("Sets the color of contaminated water in a cauldron. Mixed with dirty water if also dirty. Note: Press F3+A to reload chunks after changing.")
    int contaminatedWaterColor = 0x5f9153;

    @ConfigEntry.ColorPicker
    @Comment("Sets the color of dirty water in a cauldron. Mixed with contaminated water if also contaminated. Note: Press F3+A to reload chunks after changing.")
    int dirtyWaterColor = 0x917553;

    public int contaminatedWaterColor() {
        return contaminatedWaterColor;
    }

    public int dirtyWaterColor() {
        return dirtyWaterColor;
    }
}