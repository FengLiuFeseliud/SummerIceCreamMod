package fengliu.feseliud.item;

import fengliu.feseliud.item.icecream.IIceCream;
import fengliu.feseliud.item.icecream.IIceCreamPack;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.item.icecream.bar.PackIceCreamBar;
import fengliu.feseliud.utils.color.IColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;


public class IceCreamBarPack extends BaseColorItem{
    public IceCreamBarPack(DyeColor color, String name, int count) {
        super(color, name, count);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Item item = slotStack.getItem();
        if (!(item instanceof IIceCream)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (((IIceCream) item).getIceCreams().keySet().stream().toList().indexOf(item) != 0){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        for(IIceCreamPack packItem: ModItems.PACK_ICE_CREAM) {
            if (((BaseColorItem) packItem).getColor().getId() != this.color.getId()) {
                continue;
            }

            ItemStack packIceCreamStack = ((BaseColorItem) packItem).getDefaultStack();
            packIceCreamStack.getOrCreateNbt().put(IIceCreamPack.PACK_ICE_CREAM_KEY, slotStack.writeNbt(new NbtCompound()));

            stack.decrement(1);
            slotStack.decrement(1);

            slot.setStack(packIceCreamStack);
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
