package com.thedeathlycow.thirstful;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import me.shedaniel.autoconfig.AutoConfig;

public class ThirstfulModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ThirstfulConfig.class, parent).get();
    }
}