package fengliu.feseliud.block.entity;

import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoolerBoxBlockEntity extends InventoryBlockEntity{
    public CoolerBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.COOLER_BOX_BLOCK_ENTITY, pos, state);
        this.setMaxItemStack(27);
    }

    public void putItem(ItemStack stack, IHitSlot hitSlot){
        int slot = hitSlot.getInventoryEmptySlot(this);
        if (slot == -1){
            return;
        }
        this.setStack(slot, stack.split(stack.getCount()));
    }

    public ItemStack takeItem(IHitSlot hitSlot){
        int slot = hitSlot.getInventoryLastNotEmptySlot(this, IHitSlot.ThreeHitSlot.values());
        if (slot == -1){
            return ItemStack.EMPTY;
        }
        ItemStack stack = this.getStack(slot);
        this.setStack(slot, ItemStack.EMPTY);
        return stack;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(!(blockEntity instanceof CoolerBoxBlockEntity be)){
            return;
        }
        be.markDirty();
    }
}
