package fengliu.feseliud.utils;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.entity.InventoryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 点击槽, 将方面割分使点击方块面时返回对应槽
 */
public interface IHitSlot{

    /**
     * 槽索引
     * @return 索引
     */
    int getIndex();

    /**
     * 三点击槽
     */
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

    /**
     * 反转槽组
     * @param array 槽组
     * @return 反转后的槽组
     */
    static IHitSlot[] reverse(IHitSlot[] array) {
        IHitSlot[] newArray = new IHitSlot[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[array.length - 1 - i] = array[i];
        }
        return newArray;
    }

    /**
     * 根据点击获取槽
     * @param state 方块状态
     * @param hit 方块点击数据
     * @param hitSlot 槽组
     * @return 点击槽
     */
    static IHitSlot getHitSlot(BlockState state, BlockHitResult hit, IHitSlot[] hitSlot){
        int hitX;
        Direction direction = state.get(FacingEntityBlock.FACING).getOpposite();
        DecimalFormat df= new DecimalFormat("0.00");

        if (direction == Direction.WEST || direction == Direction.EAST){
            hitX = Integer.parseInt(String.valueOf(df.format(hit.getPos().getZ())).split("\\.")[1]);
        } else {
            hitX = Integer.parseInt(String.valueOf(df.format(hit.getPos().getX())).split("\\.")[1]);
        }

        int size = hitSlot.length;
        int wide = 100 / size;

        int index;
        for (index = 0; index < size; ++index){
            // 判断点击位置是否在该槽位置
            if ((hitX > wide * index) && (hitX < wide * (index + 1))){
                break;
            }
        }

        index = index >= size ? size - 1: index;
        if (direction == Direction.EAST || direction == Direction.NORTH){
            return reverse(hitSlot)[index];
        } else {
            return hitSlot[index];
        }
    }

    /**
     * 将库存分割成槽组的槽数份
     * @param be 库存方块实体
     * @param hitSlots 槽组
     * @return 已分割库存组
     */
    static List<SimpleInventory> splitInventor(InventoryBlockEntity be, IHitSlot[] hitSlots){
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

    /**
     * 获取该槽库存空位 slot
     * @param be 库存方块实体
     * @return 空位 slot, 没有空位返回 -1
     */
    default int getInventoryEmptySlot(InventoryBlockEntity be){
        SimpleInventory inventory = splitInventor(be, ThreeHitSlot.values()).get(this.getIndex());
        for(int index = 0; index < inventory.size(); index++){
            ItemStack stack = inventory.getStack(index);
            if (stack.isEmpty()){
                return inventory.size() * this.getIndex() + index;
            }
        }
        return -1;
    }

    /**
     * 获取该槽库存最后一个非空位 slot
     * @param be 库存方块实体
     * @param hitSlots 槽组
     * @return 非空位 slot, 没有空位返回 -1
     */
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
