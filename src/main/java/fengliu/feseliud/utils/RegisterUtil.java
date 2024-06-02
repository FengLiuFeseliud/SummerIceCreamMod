package fengliu.feseliud.utils;

import fengliu.feseliud.item.BaseColorItem;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RegisterUtil {
    public static final List<BaseColorItem> baseColorItems = new ArrayList<>();

    public static <I extends BaseItem> I registerItem(Identifier id, I item, RegistryKey<ItemGroup> group){
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(item.getDefaultStack()));
        return Registry.register(Registries.ITEM, id, item);
    }

    public static <I extends BaseItem> I registerItem(I item, RegistryKey<ItemGroup> group){
        return RegisterUtil.registerItem(IdUtil.get(item.name), item, group);
    }

    public interface colorItem{
        BaseColorItem get(DyeColor dyeColor);
    }

    public static <I extends BaseColorItem> List<I> registerColorItems(DyeColor[] dyeColors, colorItem colorItem, RegistryKey<ItemGroup> group){
        List<I> items = new ArrayList<>();
        for (DyeColor dyeColor: dyeColors){
            @SuppressWarnings("unchecked")
            I item = (I) colorItem.get(dyeColor);
            baseColorItems.add(item);
            items.add(RegisterUtil.registerItem(IdUtil.get(item.getItemName()), item, group));
        }
        return items;
    }

    public static RegistryKey<ItemGroup> registerItemGroup(String name, ItemGroup itemGroup){
        Identifier id = IdUtil.get(name);
        Registry.register(Registries.ITEM_GROUP, id, itemGroup);
        return  RegistryKey.of(RegistryKeys.ITEM_GROUP, id);
    }

    /**
     * 实例所有等级物品
     * @param levels 等级物品数组
     * @return 等级物品列表
     */
    public static <I extends ILevelItem, ITEM extends BaseItem> Map<ITEM, I> registerItems(I[] levels, RegistryKey<ItemGroup> group){
        LinkedHashMap<ITEM, I> items = new LinkedHashMap<>();
        for(I item: levels){
            items.put((ITEM) RegisterUtil.registerItem(IdUtil.get(item.getPath()), item.getItem(), group), item);
        }
        return items;
    }
}
