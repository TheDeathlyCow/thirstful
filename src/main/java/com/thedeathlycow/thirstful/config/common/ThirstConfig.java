package com.thedeathlycow.thirstful.config.common;


import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Thirstful.MODID + ".status_effect")
public class ThirstConfig implements ConfigData {

    @Comment("How long a player can go without drinking before death, in ticks (default: 2 days)")
    int maxThirstTicks = 48_000;

    boolean enableThirstDamage = false;

    @Comment("Modifies the max stack size of potions (must be between 1 and 99). Requires a restart.")
    int potionStackSize = 16;

    public int maxThirstTicks() {
        return maxThirstTicks;
    }

    public boolean enableThirstDamage() {
        return this.enableThirstDamage;
    }

    public int potionStackSize() {
        return potionStackSize;
    }

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
        this.potionStackSize = Math.clamp(this.potionStackSize, 1, 99);
    }
}