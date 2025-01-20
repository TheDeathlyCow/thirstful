package com.thedeathlycow.thirstful.world;

public record WaterPollutantContainer(
        boolean contaminated,
        boolean dirty,
        boolean salty
) {
}