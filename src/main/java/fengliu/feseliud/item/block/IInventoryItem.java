package fengliu.feseliud.item.block;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.stream.Stream;

/**
 * 方块物品 nbt 库存接口
 */
public interface IInventoryItem{

    /**
     * 从 nbt 获取物品库存
     * @param inventoryStack 物品
     * @return nbt 列表
     */
    default NbtList getInventory(ItemStack inventoryStack){
        NbtCompound nbt = BlockItem.getBlockEntityNbt(inventoryStack);
        if (nbt == null || !nbt.contains("Items", NbtElement.LIST_TYPE)) {
            return new NbtList();
        }
        return nbt.getList("Items", NbtElement.COMPOUND_TYPE);
    }

    /**
     * 从 nbt 删除物品库存
     * @param inventoryStack 物品
     */
    default void removeInventory(ItemStack inventoryStack){
        NbtCompound nbt = BlockItem.getBlockEntityNbt(inventoryStack);
        if (nbt == null || !nbt.contains("Items")) {
            return;
        }
        nbt.remove("Items");
    }

    /**
     * 获取物品库存
     * @param inventoryStack 库存
     * @return 物品列表
     */
    default Stream<ItemStack> getStacks(ItemStack inventoryStack){
        return this.getInventory(inventoryStack).stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
    }

    /**
     * 获取物品库存大小
     * @param inventoryStack 库存
     * @return 库存大小
     */
    default int size(ItemStack inventoryStack){
        return this.getInventory(inventoryStack).size();
    }

    /**
     * 获取物品库存是否为空
     * @param inventoryStack 库存
     * @return true 库存为空
     */
    default boolean isEmpty(ItemStack inventoryStack){
        return this.getInventory(inventoryStack).isEmpty();
    }

    /**
     * 操作物品库存最后一个物品
     */
    private ItemStack inventoryLastStack(ItemStack inventoryStack, boolean remove){
        NbtList nbtList = this.getInventory(inventoryStack);
        if (nbtList.isEmpty()){
            return ItemStack.EMPTY;
        }

        for (int slot = nbtList.size() - 1; slot >= 0; slot--){
            ItemStack takeStack = ItemStack.fromNbt(nbtList.getCompound(slot));
            if (takeStack.isEmpty()){
                continue;
            }

            if (!remove){
                return takeStack;
            }

            nbtList.remove(slot);
            return takeStack;
        }
        return ItemStack.EMPTY;
    }

    /**
     * 获取物品库存最后一个物品
     * @param inventoryStack 物品
     * @return 库存最后一个物品
     */
    default ItemStack getInventoryLastStack(ItemStack inventoryStack){
        return this.inventoryLastStack(inventoryStack, false);
    }

    /**
     * 取出物品库存最后一个物品, 将删除库存最后一个物品
     * @param inventoryStack 物品
     * @return 最后一个物品
     */
    default ItemStack takeInventoryLastStack(ItemStack inventoryStack){
        return this.inventoryLastStack(inventoryStack, true);
    }
}
