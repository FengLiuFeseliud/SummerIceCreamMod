package fengliu.feseliud.utils;

import fengliu.feseliud.SummerIceCream;
import net.minecraft.util.Identifier;

public class IdUtil {

    public static Identifier get(String name){
        return new Identifier(SummerIceCream.MOD_ID, name);
    }

    public static Identifier getFluidStill(String name){
        return new Identifier(SummerIceCream.MOD_ID, name + "_still");
    }

    public static Identifier getFluidFlowing(String name){
        return new Identifier(SummerIceCream.MOD_ID, name +  "_flowing");
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

    public static String getItemName(String name){
        return "item." + SummerIceCream.MOD_ID + "." + name;
    }
}
