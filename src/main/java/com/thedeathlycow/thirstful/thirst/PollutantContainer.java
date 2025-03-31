package com.thedeathlycow.thirstful.thirst;

import com.thedeathlycow.thirstful.item.component.PollutantComponent;

public record PollutantContainer(
        float dirtiness,
        float diseaseChance,
        boolean salty
) {
    public PollutantComponent toComponent() {
        return new PollutantComponent(this.dirtiness, this.diseaseChance, this.salty);
    }
}