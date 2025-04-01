package com.thedeathlycow.thirstful.config.client;

import com.thedeathlycow.thirstful.config.OptionName;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;

@IgnoreVisibility
public class ColorConfig extends ConfigSection {
    @OptionName("Contaminated water color")
    @Comment("Sets the color of contaminated water in a cauldron. Mixed with dirty water if also dirty. Note: Press F3+A to reload chunks after changing.")
    private ValidatedColor contaminatedWaterColor = new ValidatedColor(0x5f, 0x91, 0x53);

    @OptionName("Dirty water color")
    @Comment("Sets the color of dirty water in a cauldron. Mixed with contaminated water if also contaminated. Note: Press F3+A to reload chunks after changing.")
    private ValidatedColor dirtyWaterColor = new ValidatedColor(0x91, 0x75, 0x53);

    public int contaminatedWaterColor() {
        return contaminatedWaterColor.get().argb();
    }

    public int dirtyWaterColor() {
        return dirtyWaterColor.get().argb();
    }
}