package fengliu.feseliud.item.icecream;


import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 冰淇淋等级物品
 */
public interface IIceCreamLevelItem extends ILevelItem, FabricItem {
    String PREFIXED_PATH = "item/icecream/";
    String THAW_TICK_KEY = SummerIceCream.MOD_ID + ".thawTick";

    /**
     * 是否带勺
     * @return true 带勺
     */
    boolean inSpoon();

    @Override
    default boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    /**
     * 获取融化 tick
     * @return tick
     */
    default int getThawTick(){ return ((IIceCreamLevel) this.getLevelItems().get(this)).getThawTime(); }

    /**
     * 从 Nbt 获取剩余融化 tick
     * @param nbt 物品
     * @return 剩余融化 tick
     */
    default int getThawTickFromNbt(NbtCompound nbt){
        if (!nbt.contains(THAW_TICK_KEY, NbtElement.INT_TYPE)){
            int thawTime = ((IIceCreamLevel) this.getLevelItems().get(this)).getThawTime();
            nbt.putInt(THAW_TICK_KEY, thawTime);
            return thawTime;
        }
        return nbt.getInt(THAW_TICK_KEY);
    }

    /**
     * 从物品获取剩余融化 tick
     * @param stack 物品
     * @return 剩余融化 tick
     */
    default int getThawTickFromItemStack(ItemStack stack){
        return this.getThawTickFromNbt(stack.getOrCreateNbt());
    }

    /**
     * 向物品减一融化 tick
     * @param stack 物品
     */
    default void thawTickToItemStack(ItemStack stack){
        stack.getOrCreateNbt().putInt(THAW_TICK_KEY, this.getThawTickFromItemStack(stack) - 1);
    }

    /**
     * 将旧物品剩余融化 tick 设置到另一个物品
     * @param stack 物品
     * @param oldStack 旧物品
     * @return 已设置融化 tick 物品
     */
    default ItemStack setItemStack(ItemStack stack, ItemStack oldStack){
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(THAW_TICK_KEY, this.getThawTickFromItemStack(oldStack));
        return stack;
    }

    /**
     * 每 tick 在背包融化
     */
    default void thawTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)){
            return;
        }

        int thawTime = this.getThawTickFromItemStack(stack);
        if (thawTime > 0){
            stack.getOrCreateNbt().putInt(THAW_TICK_KEY, --thawTime);
            return;
        }
        player.getInventory().setStack(slot, this.thaw(stack, player));
    }

    /**
     * 融化时调用
     * @param stack 冰淇淋
     * @param player 玩家
     * @return 新冰淇淋
     */
    default ItemStack thaw(ItemStack stack, PlayerEntity player){
        ItemStack iceCreamStack = this.getNextItemStack(stack);
        stack.decrement(1);
        return iceCreamStack;
    }

    /**
     * 附加冰淇淋融化信息
     */
    default void appendThawTimeTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.translatable(IdUtil.getItemInfo("ice_cream", 1), this.getThawTickFromItemStack(stack) / 20));
    }
}
