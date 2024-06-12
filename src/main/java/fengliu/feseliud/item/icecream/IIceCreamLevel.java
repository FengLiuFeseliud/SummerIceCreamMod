package fengliu.feseliud.item.icecream;

import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.level.IItemLevel;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

/**
 * 冰淇淋等级
 */
public interface IIceCreamLevel extends IItemLevel {
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

    @Override
    default ItemStack getOutItemStack() {
        return ModItems.BAR.getDefaultStack();
    }
}
