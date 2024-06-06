package fengliu.feseliud.data.generator;

import fengliu.feseliud.SummerIceCreamDataGenerator;
import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.ModBlockItems;
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

    /**
     * 生成块模型与块状态
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        SummerIceCreamDataGenerator.forFields(ModBlocks.class.getDeclaredFields(), obj -> {
            if (obj instanceof IModBlock block){
                block.generateBlockModel(blockStateModelGenerator);
                block.generateBlockStateModel(blockStateModelGenerator);
            }
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        SummerIceCreamDataGenerator.forFields(ModItems.class.getDeclaredFields(), obj -> {
            if (obj instanceof IModItem item){
                item.generateModel(itemModelGenerator);
                return;
            }

            if (obj instanceof List<?> items){
                items.forEach(item -> ((IModItem) item).generateModel(itemModelGenerator));
                return;
            }

            if (obj instanceof Map<?, ?> items){
                items.forEach((item, level) -> ((IModItem) item).generateModel(itemModelGenerator));
            }
        });

        SummerIceCreamDataGenerator.forFields(ModBlockItems.class.getDeclaredFields(), obj -> {
            if (obj instanceof BaseBlockItem baseBlockItem){
                baseBlockItem.generateModel(itemModelGenerator);
            }
        });
    }
}
