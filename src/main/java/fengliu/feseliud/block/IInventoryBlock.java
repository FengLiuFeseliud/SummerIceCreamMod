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

/**
 * 可移动库存方块
 */
public interface IInventoryBlock {

    /**
     * 库存方块物品
     * @return 物品
     */
    ItemStack getBlockItemStack();

    /**
     * 设置新的库存方块物品
     * @return 物品
     */
    default ItemStack setBlockItemStack(ItemStack stack){
        return stack;
    }

    /**
     *  将可移动库存方块从世界上移动到玩家手上
     * @param world 世界
     * @param pos 可移动库存方块坐标
     * @param player 玩家
     * @param hand 玩家使用手
     * @return true 移动成功
     */
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

    /**
     * 将库存 nbt 写入掉落物
     * @param builderStacks builderStacks
     * @param builder builder
     * @return 掉落物表
     */
    default List<ItemStack> setNbtToDroppedStacks(List<ItemStack> builderStacks, LootContextParameterSet.Builder builder){
        if (!(builder.getOptional(LootContextParameters.BLOCK_ENTITY) instanceof InventoryBlockEntity be)){
            return builderStacks;
        }

        be.setStackNbt(this.setBlockItemStack(builderStacks.get(0)));
        return builderStacks;
    }
}
