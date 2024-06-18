package fengliu.feseliud.item.block;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.stream.Stream;

public interface IInventoryItem{

    default NbtList getInventory(ItemStack inventoryStack){
        NbtCompound nbt = BlockItem.getBlockEntityNbt(inventoryStack);
        if (nbt == null || !nbt.contains("Items", NbtElement.LIST_TYPE)) {
            return new NbtList();
        }
        return nbt.getList("Items", NbtElement.COMPOUND_TYPE);
    }

    default void removeInventory(ItemStack inventoryStack){
        NbtCompound nbt = BlockItem.getBlockEntityNbt(inventoryStack);
        if (nbt == null || !nbt.contains("Items")) {
            return;
        }
        nbt.remove("Items");
    }

    default Stream<ItemStack> getStacks(ItemStack inventoryStack){
        return this.getInventory(inventoryStack).stream().map(NbtCompound.class::cast).map(ItemStack::fromNbt);
    }

    default int size(ItemStack inventoryStack){
        return this.getInventory(inventoryStack).size();
    }

    default boolean isEmpty(ItemStack inventoryStack){
        return this.getInventory(inventoryStack).isEmpty();
    }

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

    default ItemStack getInventoryLastStack(ItemStack inventoryStack){
        return this.inventoryLastStack(inventoryStack, false);
    }

    default ItemStack takeInventoryLastStack(ItemStack inventoryStack){
        return this.inventoryLastStack(inventoryStack, true);
    }
}
