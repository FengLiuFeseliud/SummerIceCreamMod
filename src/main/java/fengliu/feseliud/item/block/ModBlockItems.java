package fengliu.feseliud.item.block;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.PlateBlockEntity;
import fengliu.feseliud.item.ModItemGroups;
import fengliu.feseliud.item.block.icecream.*;
import fengliu.feseliud.item.block.tool.Plate;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.item.Item;

import java.util.Map;

public class ModBlockItems {
    public static final BaseBlockItem ICE_CREAM_BAR_MOLD = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.ICE_CREAM_BAR_MOLD_BLOCK, 64), ModItemGroups.ITEM_GROUP);
    public static final BaseBlockItem ICE_CREAM_MACHINE = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.ICE_CREAM_MACHINE_BLOCK, 64), ModItemGroups.ITEM_GROUP);
    public static final BaseBlockItem MIXER = RegisterUtil.registerItem(new BaseBlockItem("/stop", ModBlocks.MIXER_BLOCK, 64), ModItemGroups.ITEM_GROUP);
    public static final CoolerBox COOLER_BOX = RegisterUtil.registerItem(new CoolerBox("/close", ModBlocks.COOLER_BOX_BLOCK, 1), ModItemGroups.ITEM_GROUP);
    public static final Plate PLATE = RegisterUtil.registerItem(new Plate(ModBlocks.PLATE_BLOCK, new Item.Settings().maxCount(1).maxDamage(PlateBlockEntity.SIZE)), ModItemGroups.ITEM_GROUP);
    public static final Map<IceCream, IIceCreamBlockItemLevel> ICE_CREAMS = RegisterUtil.registerItems(IceCream.IceCreamLevelsItem.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<Chocolate, Chocolate.ChocolateLevels> CHOCOLATES = RegisterUtil.registerItems(Chocolate.ChocolateLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockItemLevel> CHOCOLATE_ICE_CREAMS = RegisterUtil.registerItems(ChocolateIceCream.IceCreamLevelsItem.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockItemLevel> GOLDEN_APPLE_ICE_CREAMS = RegisterUtil.registerItems(GoldenAppleIceCream.IceCreamLevelsItem.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockItemLevel> CHORUS_FRUIT_ICE_CREAMS = RegisterUtil.registerItems(ChorusFruitIceCream.IceCreamLevelsItem.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockItemLevel> GLOW_BERRIES_ICE_CREAMS = RegisterUtil.registerItems(GlowBerriesIceCream.IceCreamLevelsItem.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockItemLevel> COOKIE_ICE_CREAMS = RegisterUtil.registerItems(CookieIceCream.IceCreamLevelsItem.values(), ModItemGroups.FOOD_GROUP);
    public static final BaseBlockItem INCOMPLETE_ICE_STAIRS = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.INCOMPLETE_ICE_STAIRS_BLOCK, 64), ModItemGroups.MATERIALS_GROUP);
    public static final BaseBlockItem INCOMPLETE_ICE_SLAB = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.INCOMPLETE_ICE_SLAB_BLOCK, 64), ModItemGroups.MATERIALS_GROUP);

    public static void registerAllBlockItems(){}
}
