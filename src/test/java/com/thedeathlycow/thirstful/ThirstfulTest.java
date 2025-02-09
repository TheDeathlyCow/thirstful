package com.thedeathlycow.thirstful;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;

public final class ThirstfulTest {

    public static void initialize() {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
        Thirstful.initialize();
    }

    private ThirstfulTest() {

    }
}