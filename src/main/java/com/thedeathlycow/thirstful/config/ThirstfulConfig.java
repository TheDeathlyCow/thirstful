package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.Config;

@IgnoreVisibility
public class ThirstfulConfig extends Config {
    private StatusEffectConfig statusEffect = new StatusEffectConfig();
    private ThirstConfig thirst = new ThirstConfig();
    private WaterPollutionConfig waterPollution = new WaterPollutionConfig();

    public ThirstfulConfig() {
        super(Thirstful.id("common"));
    }

    public StatusEffectConfig statusEffect() {
        return this.statusEffect;
    }

    public ThirstConfig thirst() {
        return this.thirst;
    }

    public WaterPollutionConfig waterPollution() {
        return this.waterPollution;
    }
}