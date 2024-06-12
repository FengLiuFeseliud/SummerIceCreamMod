package fengliu.feseliud.utils.level;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * 等级物品
 */
public interface IItemLevel extends ILevel {
    /**
     * 实例等级物品
     * @return 等级物品
     */
    IModItem getItem();

    /**
     * 超出等级返回的物品
     * @return 物品
     */
    default ItemStack getOutItemStack(){
        return Items.AIR.getDefaultStack();
    }

    default String getTranslations(String translationKey, JsonObject translations){
        String thawString;
        String thawStringKey = IdUtil.get(this.getSubName()).toTranslationKey("level");
        try {
            thawString = translations.get(thawStringKey).getAsString();
        } catch (Exception e){
            throw new RuntimeException("未添加键 "+ thawStringKey + " ?", e);
        }
        return String.format(thawString, translations.get(translationKey).getAsString());
    }
}
