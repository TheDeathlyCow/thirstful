package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.common.StatusEffectConfig;
import com.thedeathlycow.thirstful.config.common.ThirstConfig;
import com.thedeathlycow.thirstful.config.common.WaterPollutionConfig;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;

@IgnoreVisibility
@Version(version = 0)
public class ThirstfulConfig extends Config {
    @OptionName("Status Effect Config")
    @NoComment
    private StatusEffectConfig statusEffect = new StatusEffectConfig();

    @OptionName("Thirst Config")
    @NoComment
    private ThirstConfig thirst = new ThirstConfig();

    @OptionName("Water Pollution Config")
    @NoComment
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