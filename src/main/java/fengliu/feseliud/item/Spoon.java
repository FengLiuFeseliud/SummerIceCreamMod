package fengliu.feseliud.item;

import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.cup.IceCreamCup;
import fengliu.feseliud.item.icecream.potion.PotionSmoothieCup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.Map;

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
        if (!(item instanceof IIceCreamLevelItem cup)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (cup.inSpoon()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Map<? extends IIceCreamLevelItem, IIceCreamLevel> creamLevels;
        if (item instanceof IceCreamCup iceCreamCup){
            creamLevels = iceCreamCup.getIceCreamAndSpoons();
        } else if (item instanceof PotionSmoothieCup potionSmoothieCup){
            creamLevels = potionSmoothieCup.getIceCreamAndSpoons();
        } else {
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (slotStack.getNbt() == null || !slotStack.getNbt().contains(IIceCreamLevelItem.THAW_TICK_KEY, NbtElement.INT_TYPE)) {
            slotStack.getOrCreateNbt().putInt(IIceCreamLevelItem.THAW_TICK_KEY, 0);
        }

        int iceCreamLevelInt = cup.getLevelItems().get(cup).getLevel();
        creamLevels.forEach((iceCreamCupItem, iceCreamLevel) -> {
            if(iceCreamCupItem.getItemLevel().getLevel() != iceCreamLevelInt){
                return;
            }

            ItemStack iceCreamCupItemStack = ((Item) iceCreamCupItem).getDefaultStack();
            iceCreamCupItemStack.getOrCreateNbt().putInt(IIceCreamLevelItem.THAW_TICK_KEY, slotStack.getNbt().getInt(IIceCreamLevelItem.THAW_TICK_KEY));

            Potion potion = PotionUtil.getPotion(slotStack);
            if (!potion.getEffects().isEmpty()){
                PotionUtil.setPotion(iceCreamCupItemStack, potion);
            }

            stack.decrement(1);
            slotStack.decrement(1);

            slot.setStack(iceCreamCupItemStack);
        });
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
