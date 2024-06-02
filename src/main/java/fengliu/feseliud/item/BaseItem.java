package fengliu.feseliud.item;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;

public class BaseItem extends Item {
    public static final String PREFIXED_PATH = "item/";
    public final String name;

    public BaseItem(Settings settings, String name) {
        super(settings);
        this.name = name;
    }

    public BaseItem(String textureName) {
        this(new Settings().maxCount(64), textureName);
    }

    public BaseItem(String name, int count) {
        this(new Settings().maxCount(count), name);
    }

    public String getItemName(){
        return this.name;
    }

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    public String getTextureName(){
        return this.name;
    }

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    public String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    /**
     * 生成物品模型
     * @param itemModelGenerator 物品模型生成器
     */
    public void generateModel(ItemModelGenerator itemModelGenerator){
        GENERATED.upload(ModelIds.getItemModelId(this), TextureMap.layer0(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath())), itemModelGenerator.writer);
    }

    public void generateRecipe(Consumer<RecipeJsonProvider> exporter){

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.getItemName())));
    }
}
