package fengliu.feseliud.item;

import fengliu.feseliud.data.generator.IGeneratorModel;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;

import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;

/**
 *  Mod 物品接口
 */
public interface IModItem extends IGeneratorModel {
    String PREFIXED_PATH = "item/";

    /**
     * 获取物品基础名
     * @return 物品基础名
     */
    String getItemName();

    @Override
    default String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    /**
     * 生成物品模型, 参考 {@link fengliu.feseliud.data.generator.ModelDataGeneration#generateItemModels(ItemModelGenerator)}
     * @param itemModelGenerator 物品模型生成器
     */
    default void generateModel(ItemModelGenerator itemModelGenerator){
        GENERATED.upload(ModelIds.getItemModelId((Item) this), TextureMap.layer0(this.getTexturePath()), itemModelGenerator.writer);
    }

    /**
     * 生成物品配六, 参考 {@link fengliu.feseliud.data.generator.RecipeGenerator#generate(Consumer)}
     * @param exporter 配方生成器
     */
    default void generateRecipe(Consumer<RecipeJsonProvider> exporter){

    }
}
