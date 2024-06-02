package fengliu.feseliud.data.generator;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ItemDataGeneration extends FabricModelProvider {
    public ItemDataGeneration(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Field field : ModItems.class.getDeclaredFields()) {
            try {
                Object obj = field.get(null);
                if (obj instanceof BaseItem item){
                    item.generateModel(itemModelGenerator);
                }

                if (obj instanceof List<?> items){
                    items.forEach(item -> {
                        ((BaseItem) item).generateModel(itemModelGenerator);
                    });
                }

                if (obj instanceof Map<?, ?> items){
                    items.forEach((item, level) -> {
                        ((BaseItem) item).generateModel(itemModelGenerator);
                    });
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
