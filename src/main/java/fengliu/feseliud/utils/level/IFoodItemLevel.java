package fengliu.feseliud.utils.level;

import net.minecraft.item.FoodComponent;

/**
 * 食品物品等级
 */
public interface IFoodItemLevel extends IItemLevel{
    /**
     * 食品效果
     * @return 食品效果
     */
    FoodComponent getFoodComponent();
}
