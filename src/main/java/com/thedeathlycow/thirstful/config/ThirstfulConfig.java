package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Thirstful.MODID)
public class ThirstfulConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public final CommonConfig common = new CommonConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public final ClientConfig client = new ClientConfig();
}