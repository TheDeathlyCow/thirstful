package com.thedeathlycow.thirstful.config;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.config.client.ColorConfig;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;

@IgnoreVisibility
@Version(version = 0)
public class ThirstfulClientConfig extends Config {
    @OptionName("Color Config")
    @NoComment
    private ColorConfig color = new ColorConfig();

    @OptionName("Enable contaminated glint")
    @Comment("Toggle for the green glint on contaminated, dirty, and/or salty items.")
    private boolean enableContaminatedGlint = true;

    public ThirstfulClientConfig() {
        super(Thirstful.id("client"));
    }

    public ColorConfig color() {
        return color;
    }

    public boolean enableContaminatedGlint() {
        return enableContaminatedGlint;
    }
}