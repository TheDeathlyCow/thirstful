package com.thedeathlycow.thirstful.config.common;

import me.fzzyhmstrs.fzzy_config.annotations.Comment;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

@IgnoreVisibility
public class StatusEffectConfig extends ConfigSection {
    @Comment("Temperature change per tick from the cooling effect per level of cooling")
    private int coolingEffectTemperatureChange = -5;

    @Comment("Temperature change per tick from the warming effect per level of warming")
    private int warmingEffectTemperatureChange = 5;

    @Comment("Feverish entities have their temperature rise until it reaches this scale")
    @ValidatedFloat.Restrict(min = -1.0f, max = 1.0f)
    private float feverMinTemperatureScale = 0.5f;

    @Comment("Temperature rise from the fever effect per level of fever")
    @ValidatedInt.Restrict(min = 0)
    private int feverEffectTemperatureChange = 10;

    public int coolingEffectTemperatureChange() {
        return coolingEffectTemperatureChange;
    }

    public int warmingEffectTemperatureChange() {
        return warmingEffectTemperatureChange;
    }

    public float feverMinTemperatureScale() {
        return this.feverMinTemperatureScale;
    }

    public int feverEffectTemperatureChange() {
        return this.feverEffectTemperatureChange;
    }
}