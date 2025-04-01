package com.thedeathlycow.thirstful.config.common;

import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;

@IgnoreVisibility
public class WaterPollutionConfig extends ConfigSection {
    @OptionName("Enable disease")
    @NoComment
    private boolean enableDisease = true;

    @OptionName("Enable dirtiness")
    @NoComment
    private boolean enableDirtiness = true;

    @OptionName("Enable saltiness")
    @NoComment
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