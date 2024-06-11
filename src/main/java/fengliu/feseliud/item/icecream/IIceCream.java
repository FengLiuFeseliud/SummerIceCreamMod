package fengliu.feseliud.item.icecream;


import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.utils.IdUtil;
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
import java.util.Map;

public interface IIceCream extends FabricItem {
    String PREFIXED_PATH = "item/icecream/";
    String THAW_TIME_KEY = SummerIceCream.MOD_ID + ".thawTime";

    boolean inSpoon();
    <I extends IIceCream, IL extends IIceCreamLevel> Map<I, IL> getIceCreams();

    default IIceCreamLevel getIceCreamLevel(){
        return this.getIceCreams().get(this);
    }

    @Override
    default boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    default int getThawTime(){ return this.getIceCreams().get(this).getThawTime(); }

    /**
     * 获取融化下一阶段冰淇淋
     * @param stack 冰淇淋
     * @return 下一阶段冰淇淋
     */
    default ItemStack getIceThawCreamItemStack(ItemStack stack){
        boolean nextIn = false;
        Map.Entry<IIceCream, IIceCreamLevel> iceCreamEnd = null;

        for (Map.Entry<IIceCream, IIceCreamLevel> iceCream: this.getIceCreams().entrySet()){
            if (nextIn){
                ItemStack iceCreamStack = ((Item) iceCream.getKey()).getDefaultStack();
                iceCreamStack.setDamage(iceCream.getValue().getLevel() - 1);
                return iceCreamStack;
            }

            if (stack.isOf((Item) iceCream.getKey())){
                nextIn = true;
            }

            iceCreamEnd = iceCream;
        }

        if (iceCreamEnd == null){
            return ItemStack.EMPTY;
        }
        return iceCreamEnd.getValue().getAllThawItemStack();
    }

    /**
     * 每 tick 在背包融化
     */
    default void thawTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)){
            return;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(THAW_TIME_KEY, NbtElement.INT_TYPE)){
            nbt.putInt(THAW_TIME_KEY, this.getIceCreams().get(this).getThawTime());
            return;
        }

        int thawTime = nbt.getInt(THAW_TIME_KEY);
        if (thawTime > 0){
            nbt.putInt(THAW_TIME_KEY, --thawTime);
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
        ItemStack iceCreamStack = this.getIceThawCreamItemStack(stack);
        stack.decrement(1);
        return iceCreamStack;
    }

    default void appendThawTimeTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(THAW_TIME_KEY, NbtElement.INT_TYPE)){
            tooltip.add(Text.translatable(IdUtil.getItemInfo("ice_cream", 1), nbt.getInt(THAW_TIME_KEY) / 20));
        }
    }
}
