package fengliu.feseliud.utils.level;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public interface ILevelItem {
    <I extends ILevelItem, IL extends IItemLevel> Map<I, IL> getLevelItems();

    @SuppressWarnings("unchecked")
    default <I extends IItemLevel> I getItemLevel(){
        return (I) this.getLevelItems().get(this);
    }

    /**
     * 获取下一阶段物品
     * @param stack 物品
     * @return 下一阶段物品
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
