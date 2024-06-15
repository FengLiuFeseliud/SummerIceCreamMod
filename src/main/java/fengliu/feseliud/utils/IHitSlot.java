package fengliu.feseliud.utils;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.entity.InventoryBlockEntity;
import fengliu.feseliud.block.icecream.IceCreamBarMoldBlock;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public interface IHitSlot{
    int getIndex();

    enum ThreeHitSlot implements IHitSlot{
        SLOT1(0),
        SLOT2(1),
        SLOT3(2);

        private final int index;

        ThreeHitSlot(int index){
            this.index = index;
        }


        @Override
        public int getIndex() {
            return this.index;
        }
    }

    static IHitSlot[] reverse(IHitSlot[] array) {
        IHitSlot[] newArray = new IHitSlot[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[array.length - 1 - i] = array[i];
        }
        return newArray;
    }

    static IHitSlot getHitSlot(BlockState state, BlockHitResult hit, IHitSlot[] hitSlot){
        int hitX;
        Direction direction = state.get(FacingEntityBlock.FACING).getOpposite();
        DecimalFormat df= new DecimalFormat("0.00");

        if (direction == Direction.WEST || direction == Direction.EAST){
            hitX = Integer.parseInt(String.valueOf(df.format(hit.getPos().getZ())).split("\\.")[1]);
        } else {
            hitX = Integer.parseInt(String.valueOf(df.format(hit.getPos().getX())).split("\\.")[1]);
        }

        int size = 3;
        int wide = 100 / size;

        int index;
        for (index = 0; index < size; ++index){
            if ((hitX > wide * index) && (hitX < wide * (index + 1))){
                break;
            }
        }

        index = index >= 3 ? 2: index;
        if (direction == Direction.EAST || direction == Direction.NORTH){
            return reverse(hitSlot)[index];
        } else {
            return hitSlot[index];
        }
    }

    static List<SimpleInventory> splitInput(InventoryBlockEntity be, IHitSlot[] hitSlots){
        List<SimpleInventory> inventories = new ArrayList<>();
        int size = be.size() / hitSlots.length;

        SimpleInventory simpleInventory = new SimpleInventory(size);
        for (int slot = 0; slot < be.size(); slot++) {
            if (slot % size == 0 && slot != 0){
                inventories.add(simpleInventory);
                simpleInventory = new SimpleInventory(size);
            }
            simpleInventory.addStack(be.getStack(slot));
        };
        inventories.add(simpleInventory);
        return inventories;
    }

    default int getInventoryEmptySlot(InventoryBlockEntity be){
        SimpleInventory inventory = splitInput(be, ThreeHitSlot.values()).get(this.getIndex());
        for(int index = 0; index < inventory.size(); index++){
            ItemStack stack = inventory.getStack(index);
            if (stack.isEmpty()){
                return inventory.size() * this.getIndex() + index;
            }
        }
        return -1;
    }

    default int getInventoryLastNotEmptySlot(InventoryBlockEntity be, IHitSlot[] hitSlots){
        int slotSize = be.size() / hitSlots.length;
        for(int index = slotSize * (this.getIndex() + 1) - 1; slotSize * this.getIndex() <= index; index--){
            ItemStack stack = be.getStack(index);
            if (!stack.isEmpty()){
                return index;
            }
        }
        return -1;
    }
}
