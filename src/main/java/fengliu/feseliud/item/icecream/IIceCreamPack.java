package fengliu.feseliud.item.icecream;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
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
import java.util.Map;

public interface IIceCreamPack extends FabricItem {
    String ICE_CREAM_PACK_TICK_OFFSET_KEY = SummerIceCream.MOD_ID + ".iceCreamPackTickOffset";
    String ICE_CREAM_PACK_TIME_KEY = SummerIceCream.MOD_ID + ".iceCreamPackTime";
    String PACK_ICE_CREAM_KEY = SummerIceCream.MOD_ID + ".packIceCream";

    int packTickOffset();

    @Override
    default boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    /**
     * 获取被包装的冰淇淋
     * @param iceCreamPack 包装冰淇淋
     * @return 冰淇淋
     */
    default ItemStack getIceCreamItemStack(ItemStack iceCreamPack){
        NbtCompound nbt = iceCreamPack.getOrCreateNbt();
        if (!nbt.contains(PACK_ICE_CREAM_KEY, NbtElement.COMPOUND_TYPE)){
            return iceCreamPack;
        }

        ItemStack iceCreamItemStack = ItemStack.fromNbt(nbt.getCompound(PACK_ICE_CREAM_KEY));
        if (!(iceCreamItemStack.getItem() instanceof IIceCream iceCreamItem)){
            return iceCreamPack;
        }

        int iceCreamPackTime = 0;
        if (nbt.contains(ICE_CREAM_PACK_TIME_KEY, NbtElement.INT_TYPE)){
            iceCreamPackTime = nbt.getInt(ICE_CREAM_PACK_TIME_KEY);
        }

        NbtCompound iceCreamNbt = iceCreamItemStack.getOrCreateNbt();
        if (iceCreamNbt.contains(iceCreamItem.THAW_TIME_KEY, NbtElement.INT_TYPE)){
            iceCreamPackTime += iceCreamItem.getThawTime() - iceCreamNbt.getInt(iceCreamItem.THAW_TIME_KEY);
        }

        int allThawTime = 0;
        for (Map.Entry<IIceCream, IIceCreamLevel> iceCream: iceCreamItem.getIceCreams().entrySet()){
            allThawTime += iceCream.getValue().getThawTime();
            if (iceCreamPackTime > allThawTime){
                continue;
            }

            ItemStack PackIceCreamItemStack = ((BaseItem) iceCream.getKey()).getDefaultStack();
            PackIceCreamItemStack.setDamage(iceCream.getValue().getLevel());
            PackIceCreamItemStack.getOrCreateNbt().putInt(iceCreamItem.THAW_TIME_KEY, allThawTime - iceCreamPackTime);
            return PackIceCreamItemStack;
        }
        return iceCreamItem.getIceCreams().get(iceCreamItem).getAllThawItemStack();
    }

    /**
     * 在背包每 tick 增加 1 tick 取出时长
     */
    default void updatePackTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(PACK_ICE_CREAM_KEY, NbtElement.COMPOUND_TYPE)){
            return;
        }

        if (!nbt.contains(ICE_CREAM_PACK_TIME_KEY, NbtElement.INT_TYPE)){
            nbt.putInt(ICE_CREAM_PACK_TIME_KEY, 0);
            return;
        }

        if (!nbt.contains(ICE_CREAM_PACK_TICK_OFFSET_KEY, NbtElement.INT_TYPE)){
            nbt.putInt(ICE_CREAM_PACK_TICK_OFFSET_KEY, 1);
        }

        int nbtIceCreamPackTickOffset = nbt.getInt(ICE_CREAM_PACK_TICK_OFFSET_KEY);
        if (nbtIceCreamPackTickOffset >= this.packTickOffset()){
            nbt.putInt(ICE_CREAM_PACK_TIME_KEY, nbt.getInt(ICE_CREAM_PACK_TIME_KEY) + 1);
            nbt.putInt(ICE_CREAM_PACK_TICK_OFFSET_KEY, 1);
            return;
        }

        nbt.putInt(ICE_CREAM_PACK_TICK_OFFSET_KEY, nbt.getInt(ICE_CREAM_PACK_TICK_OFFSET_KEY) + 1);
    }

    default void appendPackTimeTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(ICE_CREAM_PACK_TIME_KEY, NbtElement.INT_TYPE)){
            tooltip.add(Text.translatable(IdUtil.getItemInfo("pack_ice_cream", 1), nbt.getInt(ICE_CREAM_PACK_TIME_KEY) / 20));
        }
    }
}
