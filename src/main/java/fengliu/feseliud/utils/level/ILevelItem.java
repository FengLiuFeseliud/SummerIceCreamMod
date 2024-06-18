package fengliu.feseliud.utils.level;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * 等级物品
 */
public interface ILevelItem {

    /**
     * 获取等级物品组
     * @return 等级物品组
     * @param <I> {@link ILevelItem} Mod 等级物品
     * @param <IL> {@link IItemLevel} Mod 物品等级
     */
    <I extends ILevelItem, IL extends IItemLevel> Map<I, IL> getLevelItems();

    /**
     * 获取物品等级
     * @return 物品等级
     * @param <I> {@link IItemLevel} Mod 物品等级
     */
    @SuppressWarnings("unchecked")
    default <I extends IItemLevel> I getItemLevel(){
        return (I) this.getLevelItems().get(this);
    }

    /**
     * 获取下一等级物品
     * @param stack 物品
     * @return 下一等级物品
     */
    default ItemStack getNextItemStack(ItemStack stack){
        for (Map.Entry<ILevelItem, IItemLevel> iceCream: this.getLevelItems().entrySet()){
            int level = iceCream.getValue().getLevel();
            int itemLevel = this.getItemLevel().getLevel();

            if (itemLevel + 1 == level){
                ItemStack iceCreamStack = ((Item) iceCream.getKey()).getDefaultStack();
                iceCreamStack.setDamage(itemLevel);
                return iceCreamStack;
            }
        }
        return this.getItemLevel().getOutItemStack();
    }
}
