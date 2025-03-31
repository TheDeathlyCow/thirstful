package com.thedeathlycow.thirstful.config.common;

import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;

@IgnoreVisibility
public class WaterPollutionConfig extends ConfigSection {
    private boolean enableDisease = true;

    private boolean enableDirtiness = true;

    private boolean enableSaltiness = true;

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