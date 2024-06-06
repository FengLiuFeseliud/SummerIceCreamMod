package fengliu.feseliud.item;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;

import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;

public interface IModItem {
    String PREFIXED_PATH = "item/";

    String getItemName();

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    String getTextureName();

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    default String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    /**
     * 生成物品模型
     * @param itemModelGenerator 物品模型生成器
     */
    default void generateModel(ItemModelGenerator itemModelGenerator){
        GENERATED.upload(ModelIds.getItemModelId((Item) this), TextureMap.layer0(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath())), itemModelGenerator.writer);
    }

    default void generateRecipe(Consumer<RecipeJsonProvider> exporter){

    }
}
