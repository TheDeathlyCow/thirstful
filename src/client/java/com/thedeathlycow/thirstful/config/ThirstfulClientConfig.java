package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;

@IgnoreVisibility
@Version(version = 0)
public class ThirstfulClientConfig extends Config {
    @OptionName("Color Config")
    @NoComment
    private ColorConfig color = new ColorConfig();

    public ThirstfulClientConfig() {
        super(Thirstful.id("client"));
    }

    public ColorConfig color() {
        return color;
    }
}