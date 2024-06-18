package fengliu.feseliud.block.entity;

import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlateBlockEntity extends InventoryBlockEntity{
    public static final int SIZE = 27;

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.PLATE_BLOCK_ENTITY, pos, state);
        this.setMaxItemStack(SIZE);
    }

    public boolean putItem(ItemStack stack){
        int slot = this.getEmptySlot();
        if (slot == -1){
            return false;
        }
        ItemStack pusStack = stack.copy();
        pusStack.setCount(1);
        this.setStack(slot, pusStack);
        return true;
    }

    public ItemStack takeItem(){
        int slot;
        for (slot = 0; slot < this.size(); slot++){
            ItemStack stack = this.getStack(slot);
            if (stack.isEmpty()){
                break;
            }
        }

        if (slot == 0){
            return ItemStack.EMPTY;
        }
        return this.removeStack(slot - 1);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!(blockEntity instanceof PlateBlockEntity be)){
            return;
        }

        be.markDirty();
        if (!world.getBiome(pos).value().doesNotSnow(pos) || world.getBlockState(pos.down()).isOf(Blocks.ICE)){
            return;
        }

        for (int slot = be.size() - 1; slot > 0; slot--){
            ItemStack stack = be.getStack(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof  IIceCreamLevelItem iceCreamLevelItem)){
                continue;
            }

            iceCreamLevelItem.thawTimeToItemStack(stack);
            if (iceCreamLevelItem.getThawTimeFromItemStack(stack) <= 0){
                be.setStack(slot, iceCreamLevelItem.getNextItemStack(stack));
            }
            break;
        }
    }
}
