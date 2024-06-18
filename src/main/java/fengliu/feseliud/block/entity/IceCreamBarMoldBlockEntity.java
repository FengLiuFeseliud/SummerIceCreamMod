package fengliu.feseliud.block.entity;

import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.liquid.FoodLiquidBucket;
import fengliu.feseliud.recipes.ListRecipes;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class IceCreamBarMoldBlockEntity extends InventoryBlockEntity{
    public static final int MAX_TRY_TICK = 100;
    private int tick = 0;

    public IceCreamBarMoldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.ICE_CREAM_BAR_MOLD_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(9);
    }

    protected static ItemStack getPutStack(PlayerEntity player, ItemStack stack){
        ItemStack putStack;
        if (!player.isCreative()){
            putStack = stack.split(1);
        } else {
            putStack = stack.copy();
            putStack.setCount(1);
        }
        return putStack;
    }

    public ItemStack putItem(IHitSlot hitSlots, ItemStack handStack){
        int headSlot = hitSlots.getIndex() * 3;
        for (int slotIndex = headSlot; slotIndex < headSlot + 3; slotIndex++){
            ItemStack slotStack = this.getStack(slotIndex);
            if (slotStack.isOf(handStack.getItem()) && handStack.isOf(ModItems.BAR)){
                return handStack;
            }

            if (!slotStack.isEmpty()){
                continue;
            }

            ItemStack putStack = handStack.copy();
            putStack.setCount(1);
            this.setStack(slotIndex, putStack);
            if (putStack.getItem() instanceof FoodLiquidBucket){
                return Items.BUCKET.getDefaultStack();
            }

            this.markDirty();
            return handStack;
        }
        return handStack;
    }

    public ItemStack takeItem(IHitSlot hitSlots, ItemStack handStack){
        int barSlot = hitSlots.getIndex() * 3 - 1;
        if (this.getStack(barSlot + 1).isEmpty() && handStack.isEmpty()){
            return ItemStack.EMPTY;
        }

        for (int slotIndex = barSlot + 3; slotIndex >= barSlot; slotIndex--){
            ItemStack slotStack = this.getStack(slotIndex);
            if (slotStack.isEmpty()){
                continue;
            }

            if (slotStack.getItem() instanceof FoodLiquidBucket){
                if (!handStack.isOf(Items.BUCKET)){
                    return ItemStack.EMPTY;
                }

                this.removeStack(slotIndex);
                this.markDirty();
                return slotStack;
            }

            this.removeStack(slotIndex);
            this.markDirty();
            return slotStack.copy();
        }
        return ItemStack.EMPTY;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!(blockEntity instanceof IceCreamBarMoldBlockEntity be)){
            return;
        }

        if (world.getBiome(pos).value().doesNotSnow(pos) && !world.getBlockState(pos.down()).isOf(Blocks.ICE)){
            return;
        }

        be.tick++;
        if (be.tick <= MAX_TRY_TICK){
            be.markDirty();
            return;
        }

        be.tick = 0;
        if (world.random.nextInt(20) != 0){
            return;
        }

        List<SimpleInventory> inventories = IHitSlot.splitInventor(be, IHitSlot.ThreeHitSlot.values());
        SimpleInventory inventory = inventories.get(world.random.nextInt(inventories.size()));
        Optional<ListRecipes> match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, inventory, world);

        int index = 0;
        while (match.isEmpty()){
            if (index == inventories.size()){
                return;
            }

            inventory = inventories.get(world.random.nextInt(inventories.size()));
            match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, inventory, world);
            index++;
        }

        int offset = inventories.indexOf(inventory);
        for (int slot = offset * 3; slot < (offset + 1) * 3; slot++){
            be.setStack(slot, Items.AIR.getDefaultStack());
        }
        be.setStack(offset * 3, match.get().getResult().copy());
        be.markDirty();
    }
}
