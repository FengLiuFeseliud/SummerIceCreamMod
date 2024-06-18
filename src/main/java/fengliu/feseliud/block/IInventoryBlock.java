package fengliu.feseliud.block;

import fengliu.feseliud.block.entity.InventoryBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IInventoryBlock {
    ItemStack getBlockItemStack();

    default ItemStack setBlockItemStack(ItemStack stack){
        return stack;
    }

    default boolean setItemStackToHand(World world, BlockPos pos, PlayerEntity player, Hand hand){
        if (!player.isSneaking()){
            return false;
        }

        if (!(world.getBlockEntity(pos) instanceof InventoryBlockEntity be)){
            return false;
        }

        if (!player.getStackInHand(hand).isEmpty()){
            return false;
        }

        ItemStack newStack = this.getBlockItemStack();
        be.setStackNbt(newStack);
        player.setStackInHand(hand, this.setBlockItemStack(newStack));
        world.removeBlock(pos, true);
        return true;
    }

    default List<ItemStack> setNbtToDroppedStacks(List<ItemStack> builderStacks, LootContextParameterSet.Builder builder){
        if (!(builder.getOptional(LootContextParameters.BLOCK_ENTITY) instanceof InventoryBlockEntity be)){
            return builderStacks;
        }

        be.setStackNbt(this.setBlockItemStack(builderStacks.get(0)));
        return builderStacks;
    }
}
