package fengliu.feseliud.item;

import fengliu.feseliud.data.generator.IGeneratorModel;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;

public interface IModItem extends IGeneratorModel {
    String PREFIXED_PATH = "item/";

    String getItemName();

    @Override
    default String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    default Identifier getTexturePath(){
        return IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath());
    }

    /**
     * 生成物品模型
     * @param itemModelGenerator 物品模型生成器
     */
    default void generateModel(ItemModelGenerator itemModelGenerator){
        GENERATED.upload(ModelIds.getItemModelId((Item) this), TextureMap.layer0(this.getTexturePath()), itemModelGenerator.writer);
    }

    default void generateRecipe(Consumer<RecipeJsonProvider> exporter){

    }
}
