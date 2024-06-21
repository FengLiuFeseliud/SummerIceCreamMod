package fengliu.feseliud.block.entity;

import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.cup.SmoothieCup;
import fengliu.feseliud.recipes.ListRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class IceCrusherBlockEntity extends InventoryBlockEntity {
    public static final int ICE_SLOT = 1;
    public static final int CUP_SLOT = 0;

    public IceCrusherBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.ICE_CRUSHER_BLOCK_ENTITY, pos, state);
        this.setMaxItemStack(2);
    }

    public boolean putItem(ItemStack stack, PlayerEntity player, boolean close) {
        if ((stack.isOf(ModItems.ICE_CREAM_CUPS_PACK) || stack.getItem() instanceof SmoothieCup) && this.getStack(CUP_SLOT).isEmpty()){
            if (player.isCreative()){
                ItemStack putItem = stack.copy();
                putItem.setCount(1);
                this.setStack(CUP_SLOT,  putItem);
            } else {
                this.setStack(CUP_SLOT, stack.split(1));
            }
            return true;
        }

        if (close || !stack.isOf(ModItems.ICE_CUBES.keySet().stream().toList().get(0)) && !stack.isOf(ModItems.CRUSHED_ICE.keySet().stream().toList().get(0))){
            return false;
        }

        if (player.isCreative()){
            this.setStack(ICE_SLOT,  stack.copy());
            return true;
        }
        return this.combineStack(ICE_SLOT, stack);
    }

    public boolean takeItem(PlayerEntity player, Hand hand, boolean close){
        if (!close){
            ItemStack takeItem = this.removeStack(ICE_SLOT);
            if (!takeItem.isEmpty()){
                player.setStackInHand(hand, takeItem);
                return true;
            }
        }

        ItemStack takeItem = this.removeStack(CUP_SLOT);
        if (takeItem.isEmpty()){
            return false;
        }
        player.setStackInHand(hand, takeItem);
        return true;
    }

    public boolean canCrusher(){
        return !this.getStack(CUP_SLOT).isEmpty() && !this.getStack(ICE_SLOT).isEmpty();
    }

    public void crusher(World world){
        SimpleInventory inventory = new SimpleInventory(2);
        inventory.addStack(this.getStack(CUP_SLOT));
        inventory.addStack(this.getStack(ICE_SLOT));

        Optional<ListRecipes> match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, inventory, world);
        if (match.isPresent()){
            this.setStack(CUP_SLOT, match.get().getResult().copy());
            this.getStack(ICE_SLOT).decrement(1);
        }
    }

    public static void tick(World world, BlockPos pos, BlockState blockState, BlockEntity blockEntity) {
        if (!(blockEntity instanceof IceCrusherBlockEntity be)){
            return;
        }

        be.markDirty();
    }
}
