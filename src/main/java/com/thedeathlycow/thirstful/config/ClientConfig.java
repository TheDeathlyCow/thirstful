package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Thirstful.MODID + ".client")
public class ClientConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    ColorConfig color = new ColorConfig();

    public ColorConfig color() {
        return color;
    }
}