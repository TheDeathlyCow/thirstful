package com.thedeathlycow.thirstful.config.common;


import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@IgnoreVisibility
public class ThirstConfig extends ConfigSection {
    @OptionName("Enable thirst damage")
    @NoComment
    private boolean enableThirstDamage = true;

    @OptionName("Potion item default max stack size")
    @Comment("Modifies the max stack size of potions (must be between 1 and 99). Requires a restart.")
    @ValidatedInt.Restrict(min = 1, max = 99)
    @RequiresAction(action = Action.RESTART)
    private int potionStackSize = 16;

    @OptionName("Required thirst scale for sweat")
    @Comment("The minimum thirst scale required for the player to be able to sweat.")
    @ValidatedDouble.Restrict(min = 0.0, max = 1.0)
    private double requiredThirstScaleForSweat = 0.70;

    public boolean enableThirstDamage() {
        return this.enableThirstDamage;
    }

    public int potionStackSize() {
        return potionStackSize;
    }

    public double requiredThirstScaleForSweat() {
        return requiredThirstScaleForSweat;
    }
}