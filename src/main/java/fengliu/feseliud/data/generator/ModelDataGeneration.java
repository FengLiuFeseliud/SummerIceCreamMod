package fengliu.feseliud.data.generator;

import fengliu.feseliud.SummerIceCreamDataGenerator;
import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

import java.util.List;
import java.util.Map;

public class ModelDataGeneration extends FabricModelProvider {
    public ModelDataGeneration(FabricDataOutput output) {
        super(output);
    }

    /**
     * 生成块模型与块状态
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        SummerIceCreamDataGenerator.forFields(ModBlocks.class.getDeclaredFields(), obj -> {
            try {
                if (obj instanceof IModBlock block){
                    block.generateBlockModel(blockStateModelGenerator);
                    block.generateBlockStateModel(blockStateModelGenerator);
                    return;
                }

                if (obj instanceof Map<?, ?> blocks){
                    blocks.forEach((level, block) ->{
                        ((IModBlock) block).generateBlockModel(blockStateModelGenerator);
                        ((IModBlock) block).generateBlockStateModel(blockStateModelGenerator);
                    });
                }
            } catch (Exception e){
                throw new RuntimeException("block model error ID: " + obj, e);
            }
        });
    }

    private void tryGenerateItemModel(Object itemObj, ItemModelGenerator itemModelGenerator){
        try {
            if (itemObj instanceof IModItem item){
                item.generateModel(itemModelGenerator);
                return;
            }

            if (itemObj instanceof List<?> items){
                items.forEach(item -> ((IModItem) item).generateModel(itemModelGenerator));
                return;
            }

            if (itemObj instanceof Map<?, ?> items){
                items.forEach((item, level) -> ((IModItem) item).generateModel(itemModelGenerator));
            }
        } catch (Exception e){
            throw new RuntimeException("item model error ID: " + itemObj, e);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        SummerIceCreamDataGenerator.forFields(ModItems.class.getDeclaredFields(), itemObj -> tryGenerateItemModel(itemObj, itemModelGenerator));
        SummerIceCreamDataGenerator.forFields(ModBlockItems.class.getDeclaredFields(), itemObj -> tryGenerateItemModel(itemObj, itemModelGenerator));
    }
}
