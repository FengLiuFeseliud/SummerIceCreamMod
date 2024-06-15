package fengliu.feseliud.item.block;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.item.ModItemGroups;
import fengliu.feseliud.item.block.icecream.*;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.item.Item;

import java.util.Map;

public class ModBlockItems {
    public static final BaseBlockItem ICE_CREAM_BAR_MOLD = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.ICE_CREAM_BAR_MOLD_BLOCK, 64), ModItemGroups.ITEM_GROUP);
    public static final BaseBlockItem MIXER = RegisterUtil.registerItem(new BaseBlockItem("/stop", ModBlocks.MIXER_BLOCK, 64), ModItemGroups.ITEM_GROUP);
    public static final CoolerBox COOLER_BOX = RegisterUtil.registerItem(new CoolerBox("/close", ModBlocks.COOLER_BOX_BLOCK, 1), ModItemGroups.ITEM_GROUP);
    public static final Map<IceCream, IIceCreamBlockLevel> ICE_CREAMS = RegisterUtil.registerItems(IceCream.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockLevel> CHOCOLATE_ICE_CREAMS = RegisterUtil.registerItems(ChocolateIceCream.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockLevel> GOLDEN_APPLE_ICE_CREAMS = RegisterUtil.registerItems(GoldenAppleIceCream.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockLevel> CHORUS_FRUIT_ICE_CREAMS = RegisterUtil.registerItems(ChorusFruitIceCream.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockLevel> GLOW_BERRIES_ICE_CREAMS = RegisterUtil.registerItems(GlowBerriesIceCream.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCream, IIceCreamBlockLevel> COOKIE_ICE_CREAMS = RegisterUtil.registerItems(CookieIceCream.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final BaseBlockItem INCOMPLETE_ICE_STAIRS = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.INCOMPLETE_ICE_STAIRS_BLOCK, 64), ModItemGroups.MATERIALS_GROUP);
    public static final BaseBlockItem INCOMPLETE_ICE_SLAB = RegisterUtil.registerItem(new BaseBlockItem(ModBlocks.INCOMPLETE_ICE_SLAB_BLOCK, 64), ModItemGroups.MATERIALS_GROUP);

    public static void registerAllBlockItems(){}
}
