package com.thedeathlycow.thirstful.config.client;

import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;

@IgnoreVisibility
public class ColorConfig extends ConfigSection {
    @Comment("Sets the color of contaminated water in a cauldron. Mixed with dirty water if also dirty. Note: Press F3+A to reload chunks after changing.")
    private int contaminatedWaterColor = 0x5f9153;

    @Comment("Sets the color of dirty water in a cauldron. Mixed with contaminated water if also contaminated. Note: Press F3+A to reload chunks after changing.")
    private int dirtyWaterColor = 0x917553;

    public int contaminatedWaterColor() {
        return contaminatedWaterColor;
    }

    public int dirtyWaterColor() {
        return dirtyWaterColor;
    }
}