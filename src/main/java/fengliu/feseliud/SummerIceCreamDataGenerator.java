package fengliu.feseliud;

import fengliu.feseliud.data.generator.ItemDataGeneration;
import fengliu.feseliud.data.generator.LangGeneration;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SummerIceCreamDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ItemDataGeneration::new);
		pack.addProvider(LangGeneration::new);
	}
}
