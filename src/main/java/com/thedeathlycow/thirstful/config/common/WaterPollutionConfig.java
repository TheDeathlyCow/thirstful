package com.thedeathlycow.thirstful.config.common;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;


@Config(name = Thirstful.MODID + ".water_pollution")
public class WaterPollutionConfig implements ConfigData {
    boolean enableDisease = true;

    boolean enableDirtiness = true;

    boolean enableSaltiness = true;

    public boolean enableDisease() {
        return enableDisease;
    }

    public boolean enableDirtiness() {
        return enableDirtiness;
    }

    public boolean enableSaltiness() {
        return enableSaltiness;
    }
}