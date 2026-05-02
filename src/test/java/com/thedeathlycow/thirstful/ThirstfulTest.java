package com.thedeathlycow.thirstful;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

public final class ThirstfulTest {

    public static void initialize() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Thirstful.initialize();
    }

    private ThirstfulTest() {

    }
}