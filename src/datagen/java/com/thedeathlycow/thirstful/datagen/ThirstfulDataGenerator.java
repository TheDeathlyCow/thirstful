package com.thedeathlycow.thirstful.datagen;

import com.thedeathlycow.thirstful.Thirstful;
import com.thedeathlycow.thirstful.datagen.generator.EnglishUSGenerator;
import com.thedeathlycow.thirstful.datagen.generator.TBiomeTagGenerator;
import com.thedeathlycow.thirstful.datagen.generator.TBlockTagGenerator;
import com.thedeathlycow.thirstful.datagen.generator.TItemTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ThirstfulDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        TBlockTagGenerator blockTags = pack.addProvider(TBlockTagGenerator::new);
        pack.addProvider((output, wrapperLookup) -> new TItemTagGenerator(output, wrapperLookup, blockTags));
        pack.addProvider(TBiomeTagGenerator::new);
        pack.addProvider(EnglishUSGenerator::new);
    }

    public static Identifier commonId(String path) {
        return Identifier.of("c", path);
    }

    public static Identifier scorchfulId(String path) {
        return Identifier.of("scorchful", path);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return Thirstful.MODID;
    }
}
