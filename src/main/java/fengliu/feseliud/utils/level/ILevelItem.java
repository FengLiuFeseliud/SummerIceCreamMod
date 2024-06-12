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
        boolean nextIn = false;
        Map.Entry<ILevelItem, IItemLevel> iceCreamEnd = null;

        for (Map.Entry<ILevelItem, IItemLevel> iceCream: this.getLevelItems().entrySet()){
            if (nextIn){
                ItemStack iceCreamStack = ((Item) iceCream.getKey()).getDefaultStack();
                iceCreamStack.setDamage(iceCream.getValue().getLevel() - 1);
                return iceCreamStack;
            }

            if (stack.isOf((Item) iceCream.getKey())){
                nextIn = true;
            }

            iceCreamEnd = iceCream;
        }

        if (iceCreamEnd == null){
            return ItemStack.EMPTY;
        }
        return iceCreamEnd.getValue().getOutItemStack();
    }
}
