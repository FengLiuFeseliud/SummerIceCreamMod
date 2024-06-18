package fengliu.feseliud.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;


/**
 * 库存接口
 */
public interface IInventory extends ImplementedInventory {
    /**
     * 获取库存使用格数
     * @param items 库存
     * @return 使用格数
     */
    static int getAllUseSize(DefaultedList<ItemStack> items){
        int size = 0;
        for(ItemStack stack: items){
            if (stack.isEmpty()){
                continue;
            }
            size++;
        }
        return size;
    }

    /**
     * 获取库存使用格数
     * @return 使用格数
     */
    default int getUseSize(){
        return getAllUseSize(this.getItems());
    }

    /**
     * 获取库存未使用槽位
     * @return 未使用槽位, 没有槽位返回 -1
     */
    default int getEmptySlot(){
        for(ItemStack stack: this.getItems()){
            if (!stack.isEmpty()){
                continue;
            }

            return this.getItems().indexOf(stack);
        }

        return -1;
    }
}
