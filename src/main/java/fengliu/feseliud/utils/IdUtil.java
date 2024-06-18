package fengliu.feseliud.utils;

import fengliu.feseliud.SummerIceCream;
import net.minecraft.util.Identifier;

/**
 * Mod Id 生成
 */
public class IdUtil {

    /**
     * 获取 Mod id
     * @param path 路径
     * @return id
     */
    public static Identifier get(String path){
        return new Identifier(SummerIceCream.MOD_ID, path);
    }

    /**
     * 获取 Mod 液体 still id
     * @param path 路径
     * @return id
     */
    public static Identifier getFluidStill(String path){
        return new Identifier(SummerIceCream.MOD_ID, path + "_still");
    }

    /**
     * 获取 Mod 液体 flowing id
     * @param path 路径
     * @return id
     */
    public static Identifier getFluidFlowing(String path){
        return new Identifier(SummerIceCream.MOD_ID, path +  "_flowing");
    }

    /**
     * 获取 Mod 物品组 id
     * @param name 物品组名
     * @return id
     */
    public static String getItemGroupName(String name){
        return "item.group." + SummerIceCream.MOD_ID + "." + name;
    }

    /**
     * 获取 Mod 物品提示 id
     * @param itemName 物品名
     * @return id
     */
    public static String getItemTooltip(String itemName){
        return "item." + SummerIceCream.MOD_ID + "." + itemName + ".tooltip";
    }

    /**
     * 获取 Mod 物品信息 id
     * @param name 物品名
     * @return id
     */
    public static String getItemInfo(String name){
        return SummerIceCream.MOD_ID + ".item." + name + ".info";
    }

    /**
     * 获取 Mod 物品信息 id
     * @param name 物品名
     * @param index 物品信息索引
     * @return id
     */
    public static String getItemInfo(String name, int index){
        return IdUtil.getItemInfo(name) + "." + index;
    }

    /**
     * 获取 Mod 物品名 id
     * @param name 物品名
     * @return id
     */
    public static String getItemName(String name){
        return "item." + SummerIceCream.MOD_ID + "." + name;
    }
}
