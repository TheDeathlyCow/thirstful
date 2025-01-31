package com.thedeathlycow.thirstful.config.common;

import com.thedeathlycow.thirstful.Thirstful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;


@Config(name = Thirstful.MODID + ".water_pollution")
public class WaterPollutionConfig implements ConfigData {

    boolean enableDisease = true;

    boolean enableDirtiness = true;

    boolean enableSaltiness = true;

    float defaultWaterDirtiness = 0.5f;

    float extraDirtyWaterDirtiness = 1.0f;

    float defaultWaterDiseaseChance = 0.5f;

    float extraContaminatedWaterDiseaseChance = 1.0f;

    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();

        defaultWaterDirtiness = MathHelper.clamp(defaultWaterDirtiness, 0f, 1f);
        extraDirtyWaterDirtiness = MathHelper.clamp(extraDirtyWaterDirtiness, 0f, 1f);
        defaultWaterDiseaseChance = MathHelper.clamp(defaultWaterDiseaseChance, 0f, 1f);
        extraContaminatedWaterDiseaseChance = MathHelper.clamp(extraContaminatedWaterDiseaseChance, 0f, 1f);
    }

    public boolean enableDisease() {
        return enableDisease;
    }

    public boolean enableDirtiness() {
        return enableDirtiness;
    }

    public boolean enableSaltiness() {
        return enableSaltiness;
    }

    public float defaultWaterDirtiness() {
        return defaultWaterDirtiness;
    }

    public float extraDirtyWaterDirtiness() {
        return extraDirtyWaterDirtiness;
    }

    public float defaultWaterDiseaseChance() {
        return defaultWaterDiseaseChance;
    }

    public float extraContaminatedWaterDiseaseChance() {
        return extraContaminatedWaterDiseaseChance;
    }
}