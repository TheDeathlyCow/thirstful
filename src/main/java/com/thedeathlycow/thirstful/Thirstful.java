package com.thedeathlycow.thirstful;

import com.thedeathlycow.thirstful.config.ThirstfulConfig;
import com.thedeathlycow.thirstful.registry.TStatusEffects;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thirstful implements ModInitializer {
	public static final String MODID = "thirstful";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private static ConfigHolder<ThirstfulConfig> configHolder;

	@Override
	public void onInitialize() {
		configHolder = AutoConfig.register(ThirstfulConfig.class, JanksonConfigSerializer::new); //NOSONAR

		TStatusEffects.initialize();
	}

	public static ThirstfulConfig getConfig() {
		return configHolder.getConfig();
	}

	public static Identifier id(String path) {
		return Identifier.of(MODID, path);
	}
}