package com.thedeathlycow.thirstful.config.common;


import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".status_effect")
public class ThirstConfig implements ConfigData {

    @Comment("How long a player can go without drinking before death, in ticks (default: 2 days)")
    int maxThirstTicks = 24_000;

    boolean enableThirstDamage = false;

    public int maxThirstTicks() {
        return maxThirstTicks;
    }

    public boolean enableThirstDamage() {
        return this.enableThirstDamage;
    }
}