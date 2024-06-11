package fengliu.feseliud.item;

import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.icecream.brick.*;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.MilkPopsicle;
import fengliu.feseliud.item.icecream.liquid.ChorusFruitIceCreamLiquidBucket;
import fengliu.feseliud.item.icecream.liquid.IceCreamLiquidBucket;
import fengliu.feseliud.item.icecream.bar.*;
import fengliu.feseliud.item.icecream.cup.*;
import fengliu.feseliud.utils.RegisterUtil;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;

import java.util.List;
import java.util.Map;

public class ModItems {
    public static final BaseItem BAR = register(new BaseItem("bar"), ModItemGroups.ITEM_GROUP);
    public static final BaseItem ICE_CREAM_CUPS_PACK = register(new BaseItem("ice_cream_cup_pack", 16), ModItemGroups.ITEM_GROUP);
    public static final Spoon SPOON = register(new Spoon("spoon", 16), ModItemGroups.ITEM_GROUP);
    public static final List<IceCreamBarPack> ICE_CREAM_BAR_PACKS = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new IceCreamBarPack(dyeColor,"ice_cream_bar_pack", 16), ModItemGroups.ITEM_GROUP);
    public static final List<PackIceCreamBar> PACK_ICE_CREAM = RegisterUtil.registerColorItems(DyeColor.values(), dyeColor -> new PackIceCreamBar(dyeColor,"pack_ice_cream", 1), ModItemGroups.ITEM_GROUP);

    public static final IceCreamLiquidBucket MILK_ICE_CREAM_LIQUID_BUCKET = register(new IceCreamLiquidBucket("milk_ice_cream_liquid_bucket", ModFluids.MILK_ICE_CREAM_LIQUID_FLUIDS, FoodComponents.POTATO), ModItemGroups.MATERIALS_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_BAR = RegisterUtil.registerItems(IceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_CUPS = RegisterUtil.registerItems(IceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(IceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CREAM_BRICKS = RegisterUtil.registerItems(IceCreamBrick.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final ChorusFruitIceCreamLiquidBucket CHORUS_FRUIT_ICE_CREAM_LIQUID_BUCKET = register(new ChorusFruitIceCreamLiquidBucket("chorus_fruit_ice_cream_liquid_bucket", ModFluids.CHORUS_FRUIT_CREAM_LIQUID_FLUIDS, FoodComponents.CHORUS_FRUIT), ModItemGroups.MATERIALS_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_BARS = RegisterUtil.registerItems(ChorusFruitIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_CHORUS_FRUIT_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustChorusFruitIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_CPUS = RegisterUtil.registerItems(ChorusFruitIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_CPUS_AND_SPOON = RegisterUtil.registerItems(ChorusFruitIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CHORUS_FRUIT_ICE_CREAM_CPUS = RegisterUtil.registerItems(ChocolateChorusFruitIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CHORUS_FRUIT_ICE_CREAM_CPUS_AND_SPOON = RegisterUtil.registerItems(ChocolateChorusFruitIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHORUS_FRUIT_ICE_CREAM_BRICKS = RegisterUtil.registerItems(ChorusFruitIceCreamBrick.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final IceCreamLiquidBucket COOKIE_ICE_CREAM_LIQUID_BUCKET = register(new IceCreamLiquidBucket("cookie_ice_cream_liquid_bucket", ModFluids.COOKIE_ICE_CREAM_LIQUID_FLUIDS, FoodComponents.COOKIE), ModItemGroups.MATERIALS_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_BARS = RegisterUtil.registerItems(CookieIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_COOKIE_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustCookieIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_CUPS = RegisterUtil.registerItems(CookieIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(CookieIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_COOKIE_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateCookieIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_COOKIE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateCookieIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> COOKIE_ICE_CREAM_BRICKS = RegisterUtil.registerItems(CookieIceCreamBrick.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final IceCreamLiquidBucket GLOW_BERRIES_ICE_CREAM_LIQUID_BUCKET = register(new IceCreamLiquidBucket("glow_berries_ice_cream_liquid_bucket", ModFluids.GLOW_BERRIES_CREAM_LIQUID_FLUIDS, FoodComponents.GLOW_BERRIES), ModItemGroups.MATERIALS_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_BARS = RegisterUtil.registerItems(GlowBerriesIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_GLOW_BERRIES_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustGlowBerriesIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_CUPS = RegisterUtil.registerItems(GlowBerriesIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(GlowBerriesIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GLOW_BERRIES_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateGlowBerriesIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GLOW_BERRIES_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateGlowBerriesIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GLOW_BERRIES_ICE_CREAM_BRICK = RegisterUtil.registerItems(GlowBerriesIceCreamBrick.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final IceCreamLiquidBucket GOLDEN_APPLE_ICE_CREAM_LIQUID_BUCKET = register(new IceCreamLiquidBucket("golden_apple_cream_liquid_bucket", ModFluids.GOLDEN_APPLE_CREAM_LIQUID_FLUIDS, FoodComponents.GOLDEN_APPLE), ModItemGroups.MATERIALS_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_BARS = RegisterUtil.registerItems(GoldenAppleIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_CRUST_GOLDEN_APPLE_ICE_CREAM_BARS = RegisterUtil.registerItems(ChocolateCrustGoldenAppleIceCreamBar.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_CUPS = RegisterUtil.registerItems(GoldenAppleIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(GoldenAppleIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GOLDEN_APPLE_ICE_CREAM_CUPS = RegisterUtil.registerItems(ChocolateGoldenAppleIceCreamCup.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CHOCOLATE_GOLDEN_APPLE_ICE_CREAM_CUPS_AND_SPOON = RegisterUtil.registerItems(ChocolateGoldenAppleIceCreamCup.IceCreamAndSpoonLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> GOLDEN_APPLE_ICE_CREAM_BRICKS = RegisterUtil.registerItems(GoldenAppleIceCreamBrick.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final BaseItem SALT = register(new BaseItem("salt", 64), ModItemGroups.MATERIALS_GROUP);
    public static final IceCreamLiquidBucket SALT_WATER_BUCKET = register(new IceCreamLiquidBucket("salt_water_bucket", ModFluids.SALT_WATER_FLUIDS, FoodComponents.POTATO), ModItemGroups.MATERIALS_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> SALT_WATER_POPSICLES = RegisterUtil.registerItems(SaltWaterPopsicle.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);

    public static final Map<IceCreamBar, IIceCreamLevel> MILK_POPSICLES = RegisterUtil.registerItems(MilkPopsicle.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> CRUSHED_ICE = RegisterUtil.registerItems(CrushedIce.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final Map<IceCreamBar, IIceCreamLevel> ICE_CUBES = RegisterUtil.registerItems(IceCube.IceCreamLevels.values(), ModItemGroups.FOOD_GROUP);
    public static final SerratedKnife SERRATED_KNIFE = register(new SerratedKnife(new Item.Settings().maxCount(1).maxDamage(32), "serrated_knife"), ModItemGroups.ITEM_GROUP);

    private static <I extends IModItem> I register(I item, RegistryKey<ItemGroup> group){
        return RegisterUtil.registerItem(item, group);
    }

    public static void registerAllItems(){}
}
