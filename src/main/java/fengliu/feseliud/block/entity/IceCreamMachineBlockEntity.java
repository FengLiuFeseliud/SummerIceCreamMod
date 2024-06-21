package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.tool.IceCreamMachineBlock;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.cup.IceCreamCup;
import fengliu.feseliud.item.icecream.liquid.FoodLiquidBucket;
import fengliu.feseliud.recipes.ListRecipes;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class IceCreamMachineBlockEntity extends InventoryBlockEntity{
    public static final int CUP_SLOT = 0;
    public static final int USE_TICK = 200;
    private final int[] ticks = new int[]{0, 0};

    public final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return ticks[index];
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 2;
        }
    };

    public IceCreamMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.ICE_CREAM_MACHINE_BLOCK_ENTITY, pos, state);
        this.setMaxItemStack(20);
    }

    public ItemStack putItem(ItemStack stack, IHitSlot hitSlot){
        int slot = -1;
        if ((stack.isOf(ModItems.ICE_CREAM_CUPS_PACK) || stack.getItem() instanceof IceCreamCup) && hitSlot.isSlotEmpty(this, CUP_SLOT)){
            slot = hitSlot.getInventorySlot(this, CUP_SLOT);
        } else if(stack.getItem() instanceof FoodLiquidBucket bucket) {
            SimpleInventory inventory = hitSlot.getHitSlotInventory(this);
            if (!inventory.getStack(CUP_SLOT + 1).isOf(bucket) && !inventory.getStack(CUP_SLOT + 1).isEmpty()){
                return ItemStack.EMPTY;
            }

            slot = hitSlot.getHeadSlot(this) + 1 + inventory.count(bucket);
        }

        if (slot == -1  || slot >= hitSlot.getHeadSlot(this) + hitSlot.getHitSlotInventorySize(this)){
            return ItemStack.EMPTY;
        }

        if (stack.getItem() instanceof FoodLiquidBucket){
            this.setStack(slot, stack.copy());
            return Items.BUCKET.getDefaultStack();
        } else{
            this.setStack(slot, new ItemStack(stack.getItem(), 1));
        }
        return stack;
    }

    public ItemStack takeItem(ItemStack stack, IHitSlot hitSlot){
        if (stack.isOf(Items.BUCKET)){
            int slot = hitSlot.getInventoryLastNotEmptySlot(this);
            if (slot == -1 || !(this.getStack(slot).getItem() instanceof FoodLiquidBucket)){
                return ItemStack.EMPTY;
            }
            return this.removeStack(slot);
        }

        int slot = hitSlot.getInventorySlot(this, CUP_SLOT);
        if (this.getStack(slot).getItem() instanceof FoodLiquidBucket){
            return ItemStack.EMPTY;
        }
        return this.removeStack(slot);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!(blockEntity instanceof IceCreamMachineBlockEntity be)){
            return;
        }

        int index = 0;
        for (IHitSlot hitSlot: IceCreamMachineBlock.HIT_SLOTS){
            SimpleInventory inventory = new SimpleInventory(2);
            inventory.addStack(hitSlot.getInventoryLastStack(be));

            int slot = hitSlot.getInventorySlot(be, 0);
            inventory.addStack(be.getStack(slot));

            Optional<ListRecipes> match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, inventory, world);
            if (match.isPresent()){
                if (be.ticks[index] < USE_TICK){
                    be.ticks[index] =  be.ticks[index] + 1;
                } else {
                    be.removeStack(hitSlot.getInventoryLastNotEmptySlot(be));
                    be.setStack(slot, match.get().getResult().copy());
                }
            } else {
                be.ticks[index] = 0;
            }
            index++;
        }
        be.markDirty();
    }
}
