package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Thirstful.MODID)
public class ThirstfulConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    CommonConfig common = new CommonConfig();

    @ConfigEntry.Gui.CollapsibleObject
    ClientConfig client = new ClientConfig();

    public CommonConfig common() {
        return common;
    }

    public ClientConfig client() {
        return client;
    }
}