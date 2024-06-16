package fengliu.feseliud.utils.level;

import net.minecraft.item.FoodComponent;

public interface IFoodItemLevel extends IItemLevel{
    /**
     * 食品效果
     * @return 食品效果
     */
    FoodComponent getFoodComponent();
}
