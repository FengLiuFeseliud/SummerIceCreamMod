package fengliu.feseliud.item;

import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.cup.IceCreamCup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public class Spoon extends BaseItem{
    public Spoon(String name, int count) {
        super(name, count);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Item item = slotStack.getItem();
        if (!(item instanceof IceCreamCup iceCreamCup)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (iceCreamCup.isSpoon()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (slotStack.getNbt() == null || !slotStack.getNbt().contains(IIceCreamLevelItem.THAW_TIME_KEY, NbtElement.INT_TYPE)) {
            slotStack.getOrCreateNbt().putInt(IIceCreamLevelItem.THAW_TIME_KEY, 0);
        }

        int iceCreamLevelInt = iceCreamCup.getLevelItems().get(iceCreamCup).getLevel();
        iceCreamCup.getIceCreamAndSpoons().forEach((iceCreamCupItem, iceCreamLevel) -> {
            if(iceCreamLevel.getLevel() != iceCreamLevelInt){
                return;
            }

            ItemStack iceCreamCupItemStack = iceCreamCupItem.getDefaultStack();
            iceCreamCupItemStack.getOrCreateNbt().putInt(IIceCreamLevelItem.THAW_TIME_KEY, slotStack.getNbt().getInt(IIceCreamLevelItem.THAW_TIME_KEY));

            stack.decrement(1);
            slotStack.decrement(1);

            slot.setStack(iceCreamCupItemStack);
        });
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
