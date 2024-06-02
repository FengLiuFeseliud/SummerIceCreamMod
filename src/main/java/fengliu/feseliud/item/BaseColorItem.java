package fengliu.feseliud.item;

import com.google.gson.JsonObject;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.color.IColor;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class BaseColorItem extends BaseItem implements IColor {
    public final DyeColor color;

    public BaseColorItem(Settings settings,  DyeColor color, String name) {
        super(settings, name);
        this.color = color;
    }

    public BaseColorItem(DyeColor color, String name) {
        this(new Settings().maxCount(64), color, name);
    }

    public BaseColorItem(DyeColor color, String name, int count) {
        this(new Settings().maxCount(count), color, name);
    }

    @Override
    public String getItemName() {
        return this.color.getName() + "_" + this.name;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    public String setColorTranslation(Map<String, String> colorTranslations, String translationKey, JsonObject translations){
        return String.format(translations.get(translationKey).getAsString(), colorTranslations.get(this.getColor().getName()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.getTextureName())));
    }
}
