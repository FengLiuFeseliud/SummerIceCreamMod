package fengliu.feseliud.item.icecream;


import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IIceCreamLevelItem extends ILevelItem, FabricItem {
    String PREFIXED_PATH = "item/icecream/";
    String THAW_TIME_KEY = SummerIceCream.MOD_ID + ".thawTime";

    boolean inSpoon();

    @Override
    default boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    default int getThawTime(){ return ((IIceCreamLevel) this.getLevelItems().get(this)).getThawTime(); }

    default int getThawTimeFromItemStack(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(THAW_TIME_KEY, NbtElement.INT_TYPE)){
            int thawTime = ((IIceCreamLevel) this.getLevelItems().get(this)).getThawTime();
            nbt.putInt(THAW_TIME_KEY, thawTime);
            return thawTime;
        }
        return nbt.getInt(THAW_TIME_KEY);
    }

    default ItemStack setItemStack(ItemStack stack, ItemStack oldStack){
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(THAW_TIME_KEY, this.getThawTimeFromItemStack(oldStack));
        return stack;
    }

    /**
     * 每 tick 在背包融化
     */
    default void thawTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)){
            return;
        }

        int thawTime = this.getThawTimeFromItemStack(stack);
        if (thawTime > 0){
            stack.getOrCreateNbt().putInt(THAW_TIME_KEY, --thawTime);
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

    default void appendThawTimeTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        tooltip.add(Text.translatable(IdUtil.getItemInfo("ice_cream", 1), this.getThawTimeFromItemStack(stack) / 20));
    }
}
