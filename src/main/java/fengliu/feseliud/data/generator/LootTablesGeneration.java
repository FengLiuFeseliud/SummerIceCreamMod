package fengliu.feseliud.data.generator;

import fengliu.feseliud.SummerIceCreamDataGenerator;
import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.loot.FabricBlockLootTableGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import java.util.Map;

public class LootTablesGeneration extends FabricBlockLootTableProvider {
    public LootTablesGeneration(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        SummerIceCreamDataGenerator.forFields(ModBlocks.class.getDeclaredFields(), obj -> {
            if (obj instanceof IModBlock block){
                block.generateLootItem(this);
                return;
            }

            if (obj instanceof Map<?, ?> blocks){
                blocks.forEach((level, block) ->{
                    ((IModBlock) block).generateLootItem(this);
                });
            }
        });
    }
}
