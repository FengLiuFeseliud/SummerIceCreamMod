package fengliu.feseliud.utils.color;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

public interface IColor {
    int getColor();

    default DyeColor getDyeColor(){
        return DyeColor.byFireworkColor(this.getColor());
    }

    default int colorProvider(ItemStack stack, int tintIndex){
        return this.getColor();
    }
}
