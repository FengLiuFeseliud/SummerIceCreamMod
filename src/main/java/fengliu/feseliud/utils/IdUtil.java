package fengliu.feseliud.utils;

import fengliu.feseliud.SummerIceCream;
import net.minecraft.util.Identifier;

public class IdUtil {

    public static Identifier get(String name){
        return new Identifier(SummerIceCream.MOD_ID, name);
    }

    public static String getItemGroupName(String name){
        return "item.group." + SummerIceCream.MOD_ID + "." + name;
    }

    public static String getItemTooltip(String itemName){
        return "item." + SummerIceCream.MOD_ID + "." + itemName + ".tooltip";
    }

    public static String getItemInfo(String name){
        return SummerIceCream.MOD_ID + ".item." + name + ".info";
    }

    public static String getItemInfo(String name, int index){
        return IdUtil.getItemInfo(name) + "." + index;
    }
}
