package fengliu.feseliud.block.entity;

import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.item.icecream.liquid.IceCreamLiquidBucket;
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
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IceCreamBarMoldBlockEntity extends InventoryBlockEntity{
    public static final int freezingUesTick = 12 * 50 * 20;
    public int[] freezingTick = new int[]{0, 0, 0};

    public IceCreamBarMoldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.ICE_CREAM_BAR_MOLD_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(9);
    }

    public void putItem(PlayerEntity player, IHitSlot hitSlots, ItemStack stack){
        int barSlot = hitSlots.getIndex() * 3;
        ItemStack barSlotStack = this.getStack(barSlot);
        if (this.getStack(barSlot).getItem() instanceof IceCreamBar){
            return;
        }

        if (!barSlotStack.isOf(ModItems.BAR)){
            if (!stack.isOf(ModItems.BAR)){
                return;
            }

            this.setStack(barSlot, ModItems.BAR.getDefaultStack());
            if (!player.isCreative()){
                stack.decrement(1);
            }
            this.syncInventory();
            return;
        }

        for (int slotIndex = barSlot + 1; slotIndex < barSlot + 3; slotIndex++){
            ItemStack slotStack = this.getStack(slotIndex);
            if (!slotStack.isEmpty()){
                continue;
            }

            ItemStack newSlotStack = stack.copy();
            newSlotStack.setCount(1);

            this.setStack(slotIndex, newSlotStack);
            if (newSlotStack.getItem() instanceof IceCreamLiquidBucket){
                assert world != null;
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), Items.BUCKET.getDefaultStack()));
            }

            if (!player.isCreative()){
                stack.decrement(1);
            }
            this.syncInventory();
            return;
        }
    }

    public void takeItem(PlayerEntity player, IHitSlot hitSlots, World world, BlockPos pos, ItemStack handStack){
        int barSlot = hitSlots.getIndex() * 3 - 1;
        for (int slotIndex = barSlot + 3; slotIndex >= barSlot; slotIndex--){
            ItemStack slotStack = this.getStack(slotIndex);
            if (slotStack == null){
                continue;
            }

            if (slotStack.isEmpty()){
                continue;
            }

            if (slotStack.getItem() instanceof IceCreamLiquidBucket){
                if (handStack.isOf(Items.BUCKET)){
                    if (!player.isCreative()){
                        handStack.decrement(1);
                    }
                } else {
                    return;
                }
            }

            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), slotStack));
            this.setStack(slotIndex, Items.AIR.getDefaultStack());
            this.syncInventory();
            return;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!(blockEntity instanceof IceCreamBarMoldBlockEntity be)){
            return;
        }

        // 仅在下方是冰或者当前生物群系下能结冰时才能进行制做雪糕
        if (!world.getBlockState(pos.down()).isOf(Blocks.ICE) && !world.getBiome(pos).value().isCold(pos)){
            return;
        }

        List<SimpleInventory> inventories = IHitSlot.splitInput(be, IHitSlot.ThreeHitSlot.values());
        for (SimpleInventory inventory : inventories) {
            int index = inventories.indexOf(inventory);
            Optional<ListRecipes> match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, inventory, world);
            if (match.isPresent()) {
                be.freezingTick[index]++;
                if (be.freezingTick[index] <= IceCreamBarMoldBlockEntity.freezingUesTick) {
                    continue;
                }

                int offset = inventories.indexOf(inventory);
                for (int slot = offset * 3; slot < (offset + 1) * 3; slot++){
                    be.setStack(slot, Items.AIR.getDefaultStack());
                }
                be.setStack(offset * 3, match.get().getResult().copy());
                be.syncInventory();
            } else {
                be.freezingTick[index] = 0;
            }
        }
    }

}
