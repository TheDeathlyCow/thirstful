package com.thedeathlycow.thirstful.config.common;


import com.thedeathlycow.thirstful.config.NoComment;
import com.thedeathlycow.thirstful.config.OptionName;
import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.util.Mth;

@IgnoreVisibility
public class ThirstConfig extends ConfigSection {
    @OptionName("Max thirst ticks multipliers")
    @Comment("Multiplies how long a player can go without drinking before death, in ticks (default: 2 days).")
    private float maxThirstTicksMultiplier = 1.0f;

    @OptionName("Enable thirst damage")
    @NoComment
    private boolean enableThirstDamage = false;

    @OptionName("Potion item default max stack size")
    @Comment("Modifies the max stack size of potions (must be between 1 and 99). Requires a restart.")
    @ValidatedInt.Restrict(min = 1, max = 99)
    @RequiresAction(action = Action.RESTART)
    private int potionStackSize = 16;

    public int maxThirstTicks() {
        return Mth.floor(48_000 * maxThirstTicksMultiplier);
    }

    public boolean enableThirstDamage() {
        return this.enableThirstDamage;
    }

    public int potionStackSize() {
        return potionStackSize;
    }
}