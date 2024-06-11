package fengliu.feseliud.item.icecream;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.ILevelItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

/**
 * 冰淇淋等级
 */
public interface IIceCreamLevel extends ILevelItem {
    /**
     * 冰淇淋食品效果
     * @return 食品效果
     */
    FoodComponent getFoodComponent();
    /**
     * 冰淇淋融化时间
     * @return 融化时间
     */
    int getThawTime();

    /**
     * 完全融化后返回的物品
     * @return 物品
     */
    default ItemStack getAllThawItemStack(){
        return ModItems.BAR.getDefaultStack();
    }

    /**
     * 冰淇淋融化状态 ID
     * @return 状态 ID
     */
    String getThawName();

    @Override
    default String getIdName() {
        return this.getName() + "_" + this.getThawName();
    }

    @Override
    default String getTranslations(String translationKey, JsonObject translations){
        String thawString;
        String thawStringKey = IdUtil.get(this.getThawName()).toTranslationKey("thaw");
        try {
            thawString = translations.get(thawStringKey).getAsString();
        } catch (Exception e){
            throw new RuntimeException("未添加键 "+ thawStringKey + " ?", e);
        }
        return String.format(thawString, translations.get(translationKey).getAsString());
    }
}
