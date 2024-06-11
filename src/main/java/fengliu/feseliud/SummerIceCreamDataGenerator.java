package fengliu.feseliud;

import fengliu.feseliud.data.generator.ModelDataGeneration;
import fengliu.feseliud.data.generator.LangGeneration;
import fengliu.feseliud.data.generator.LootTablesGeneration;
import fengliu.feseliud.data.generator.RecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class SummerIceCreamDataGenerator implements DataGeneratorEntrypoint {
	public static void forFields(Field[] fields, Consumer<Object> consumer){
		for (Field field : fields) {
			try {
				consumer.accept(field.get(null));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelDataGeneration::new);
		pack.addProvider(LangGeneration::new);
		pack.addProvider(LootTablesGeneration::new);
		pack.addProvider(RecipeGenerator::new);
	}
}
