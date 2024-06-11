package fengliu.feseliud.data.generator;

import fengliu.feseliud.SummerIceCreamDataGenerator;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    private void tryGenerateRecipe(Object itemObj, Consumer<RecipeJsonProvider> exporter){
        try {
            if (itemObj instanceof IModItem item){
                item.generateRecipe(exporter);
                return;
            }

            if (itemObj instanceof List<?> items){
                items.forEach(item -> ((IModItem) item).generateRecipe(exporter));
                return;
            }

            if (itemObj instanceof Map<?, ?> items){
                items.forEach((item, level) -> ((IModItem) item).generateRecipe(exporter));
            }
        } catch (Exception e){
            throw new RuntimeException("item recipe error ID: " + itemObj, e);
        }
    }

    /**
     * 生成物品配方
     * <p>
     * 通过调用 {@link IModItem#generateRecipe(Consumer)} 生成 物品/块物品 配方
     */
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        SummerIceCreamDataGenerator.forFields(ModItems.class.getDeclaredFields(), itemObj -> tryGenerateRecipe(itemObj, exporter));
        SummerIceCreamDataGenerator.forFields(ModBlockItems.class.getDeclaredFields(), itemObj -> tryGenerateRecipe(itemObj, exporter));
    }
}
