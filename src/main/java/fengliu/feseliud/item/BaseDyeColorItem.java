package fengliu.feseliud.item;

import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.color.IColorItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 简单颜色物品
 */
public class BaseDyeColorItem extends BaseItem implements IColorItem {
    public final DyeColor color;

    public BaseDyeColorItem(Settings settings, DyeColor color, String name) {
        super(settings, name);
        this.color = color;
    }

    public BaseDyeColorItem(DyeColor color, String name) {
        this(new Settings().maxCount(64), color, name);
    }

    public BaseDyeColorItem(DyeColor color, String name, int count) {
        this(new Settings().maxCount(count), color, name);
    }

    @Override
    public String getItemName() {
        return this.color.getName() + "_" + this.name;
    }

    @Override
    public int getColor() {
        return this.color.getFireworkColor();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.getTextureName())));
    }
}
