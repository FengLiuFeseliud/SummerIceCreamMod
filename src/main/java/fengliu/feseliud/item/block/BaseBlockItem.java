package fengliu.feseliud.item.block;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;


public class BaseBlockItem extends BlockItem implements IModItem {
    public static final String PREFIXED_PATH = "block/";
    public final String subName;
    public final String name;

    public BaseBlockItem(String subName, IModBlock block, Settings settings) {
        super((Block) block, settings);
        this.name = block.getBlockName();
        this.subName = subName;
    }

    public BaseBlockItem(IModBlock block, Settings settings) {
        this("", block, settings);
    }

    public BaseBlockItem(String subName, IModBlock block, int count) {
        this(subName, block, new Settings().maxCount(count));
    }

    public BaseBlockItem(IModBlock block, int count) {
        this("", block, count);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.name)));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public String getSubName(){
        return this.subName;
    }

    @Override
    public String getItemName() {
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
        itemModelGenerator.register(this,
                new Model(Optional.of(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath()).withSuffixedPath(this.getSubName())), Optional.empty()));
    }
}
