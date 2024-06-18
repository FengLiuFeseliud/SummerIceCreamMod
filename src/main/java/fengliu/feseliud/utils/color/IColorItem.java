package fengliu.feseliud.utils.color;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Map;

/**
 * 颜色物品
 */
public interface IColorItem {
    /**
     * 获取颜色
     * @return 16位颜色
     */
    int getColor();

    /**
     * 获取染料颜色
     * @return 染料颜色
     */
    default DyeColor getDyeColor(){
        return DyeColor.byFireworkColor(this.getColor());
    }

    /**
     * 获取颜色物品翻译
     * @param colorTranslations 颜色翻译组
     * @param translationKey 翻译键
     * @param translations 翻译文件 json
     * @return 翻译
     */
    default String setColorTranslation(Map<String, String> colorTranslations, String translationKey, JsonObject translations){
        return String.format(translations.get(translationKey).getAsString(), colorTranslations.get(this.getDyeColor().getName()));
    }

    /**
     *  向颜色提供器提供颜色
     * @param stack 物品
     * @param tintIndex 材质层索引
     * @return 16位颜色
     */
    default int colorProvider(ItemStack stack, int tintIndex){
        return this.getColor();
    }
}
