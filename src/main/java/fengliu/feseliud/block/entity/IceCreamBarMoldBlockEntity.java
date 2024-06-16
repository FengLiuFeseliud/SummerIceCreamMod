package fengliu.feseliud.block.entity;

import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.item.icecream.liquid.FoodLiquidBucket;
import fengliu.feseliud.recipes.ListRecipes;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public ItemStack putItem(PlayerEntity player, IHitSlot hitSlots, ItemStack stack){
        int barSlot = hitSlots.getIndex() * 3;
        ItemStack barSlotStack = this.getStack(barSlot);
        if (this.getStack(barSlot).getItem() instanceof IceCreamBar){
            return ItemStack.EMPTY;
        }

        if (stack.isOf(ModItems.BAR)){
            if (barSlotStack.isOf(ModItems.BAR)){
                return ItemStack.EMPTY;
            }

            this.setStack(barSlot, getPutStack(player, stack));
            this.markDirty();
            return ItemStack.EMPTY;
        }

        for (int slotIndex = barSlot + 1; slotIndex < barSlot + 3; slotIndex++){
            ItemStack slotStack = this.getStack(slotIndex);
            if (!slotStack.isEmpty()){
                continue;
            }

            ItemStack putStack = getPutStack(player, stack);
            this.setStack(slotIndex, putStack);
            if (putStack.getItem() instanceof FoodLiquidBucket){
                assert world != null;
                return Items.BUCKET.getDefaultStack();
            }

            this.markDirty();
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public ItemStack takeItem(PlayerEntity player, Hand hand, IHitSlot hitSlots, World world, BlockPos pos, ItemStack handStack){
        int barSlot = hitSlots.getIndex() * 3 - 1;
        for (int slotIndex = barSlot + 3; slotIndex >= barSlot; slotIndex--){
            ItemStack slotStack = this.getStack(slotIndex);
            if (slotStack.isEmpty()){
                continue;
            }

            if (slotStack.getItem() instanceof FoodLiquidBucket){
                if (!handStack.isOf(Items.BUCKET)){
                    return ItemStack.EMPTY;
                }

                if (!player.isCreative()){
                    handStack.decrement(1);
                }
                this.setStack(slotIndex, ItemStack.EMPTY);
                return slotStack;
            }

            ItemStack takeStack = slotStack.copy();
            this.setStack(slotIndex, ItemStack.EMPTY);
            this.markDirty();
            return takeStack;
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
        be.markDirty();
        if (be.tick <= MAX_TRY_TICK){
            return;
        }

        be.tick = 0;
        if (world.random.nextInt(20) != 0){
            return;
        }

        List<SimpleInventory> inventories = IHitSlot.splitInput(be, IHitSlot.ThreeHitSlot.values());
        SimpleInventory inventory = inventories.get(world.random.nextInt(inventories.size()));

        int index = 0;
        while (inventory.getStack(0).getItem() instanceof IceCreamBar){
            if (index == inventories.size()){
                return;
            }

            inventory = inventories.get(world.random.nextInt(inventories.size()));
            index++;
        }

        Optional<ListRecipes> match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, inventory, world);
        if (match.isEmpty()) {
            return;
        }

        int offset = inventories.indexOf(inventory);
        for (int slot = offset * 3; slot < (offset + 1) * 3; slot++){
            be.setStack(slot, Items.AIR.getDefaultStack());
        }
        be.setStack(offset * 3, match.get().getResult().copy());
        be.markDirty();
    }
}
