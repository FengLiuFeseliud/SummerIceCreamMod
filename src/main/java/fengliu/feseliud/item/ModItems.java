package fengliu.feseliud.item;

import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.*;
import fengliu.feseliud.item.icecream.cup.*;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.util.DyeColor;

import java.util.List;
import java.util.Map;

public class ModItems {
    public static final BaseItem BAR = register(new BaseItem("bar"));
    public static final BaseItem ICE_CREAM_CUPS_PACK = register(new BaseItem("ice_cream_cup_pack", 16));
    public static final Spoon SPOON = register(new Spoon("spoon", 16));
    public static final List<IceCreamBarPack> ICE_CREAM_BAR_PACKS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new IceCreamBarPack(dyeColor,"ice_cream_bar_pack", 16), ModItemGroups.ITEM_GROUP);
    public static final List<PackIceCreamBar> PACK_ICE_CREAM = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new PackIceCreamBar(dyeColor,"pack_ice_cream", 1), ModItemGroups.ITEM_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_BAR = RegisterUtil.registerItems(IceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_CUPS = RegisterUtil.registerItems(IceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(IceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_BARS = RegisterUtil.registerItems(ChorusFruitIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_CHORUS_FRUIT_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustChorusFruitIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_CPUS = RegisterUtil.registerItems(ChorusFruitIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_CPUS_AND_SPOON = RegisterUtil.registerItems(ChorusFruitIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CHORUS_FRUIT_ICE_CREAM_CPUS = RegisterUtil.registerItems(ChocolateChorusFruitIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CHORUS_FRUIT_ICE_CREAM_CPUS_AND_SPOON = RegisterUtil.registerItems(ChocolateChorusFruitIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_BARS = RegisterUtil.registerItems(CookieIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_COOKIE_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustCookieIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_CUPS = RegisterUtil.registerItems(CookieIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(CookieIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_COOKIE_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateCookieIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_COOKIE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateCookieIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_BARS = RegisterUtil.registerItems(GlowBerriesIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_GLOW_BERRIES_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustGlowBerriesIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_CUPS = RegisterUtil.registerItems(GlowBerriesIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(GlowBerriesIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GLOW_BERRIES_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateGlowBerriesIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GLOW_BERRIES_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateGlowBerriesIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_BARS = RegisterUtil.registerItems(GoldenAppleIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_GOLDEN_APPLE_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustGoldenAppleIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_CUPS = RegisterUtil.registerItems(GoldenAppleIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(GoldenAppleIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GOLDEN_APPLE_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateGoldenAppleIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GOLDEN_APPLE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateGoldenAppleIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> SALT_WATER_POPSICLE = RegisterUtil.registerItems(SaltWaterPopsicle.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    private static <I extends BaseItem> I register(I item){
        return RegisterUtil.registerItem(item, ModItemGroups.ITEM_GROUP);
    }

    public static void registerAllItems(){}
}
