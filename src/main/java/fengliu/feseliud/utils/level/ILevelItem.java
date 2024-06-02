package fengliu.feseliud.utils.level;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.item.Item;

/**
 * 等级物品
 */
public interface ILevelItem extends ILevel {
    /**
     * 实例等级物品
     * @return 等级物品
     */
    BaseItem getItem();

    String getTranslations(String translationKey, JsonObject translations);
}
