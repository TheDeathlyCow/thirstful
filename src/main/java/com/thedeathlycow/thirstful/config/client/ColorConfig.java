package com.thedeathlycow.thirstful.config.client;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Thirstful.MODID + ".color")
public class ColorConfig implements ConfigData {
    @ConfigEntry.ColorPicker
    int contaminatedWaterColor = 0x42f584;

    @ConfigEntry.ColorPicker
    int dirtyWaterColor = 0x917553;

    public int contaminatedWaterColor() {
        return contaminatedWaterColor;
    }

    public int dirtyWaterColor() {
        return dirtyWaterColor;
    }
}