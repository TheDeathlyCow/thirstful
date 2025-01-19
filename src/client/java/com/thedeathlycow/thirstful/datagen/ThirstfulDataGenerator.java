package com.thedeathlycow.thirstful.datagen;

import com.thedeathlycow.thirstful.datagen.generator.TBlockTagGenerator;
import com.thedeathlycow.thirstful.datagen.generator.TItemTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ThirstfulDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		TBlockTagGenerator blockTags = pack.addProvider(TBlockTagGenerator::new);
		pack.addProvider((output, wrapperLookup) -> new TItemTagGenerator(output, wrapperLookup, blockTags));
	}
}
