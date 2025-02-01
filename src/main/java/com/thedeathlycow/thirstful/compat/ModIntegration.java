package com.thedeathlycow.thirstful.compat;

import net.fabricmc.loader.api.FabricLoader;

public final class ModIntegration {
    public static boolean isScorchfulLoaded() {
        return FabricLoader.getInstance().isModLoaded("scorchful");
    }

    private ModIntegration() {

    }
}