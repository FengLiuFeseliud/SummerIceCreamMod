package fengliu.feseliud.block;

import fengliu.feseliud.block.icecream.*;
import fengliu.feseliud.block.tool.*;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.block.Chocolate;
import fengliu.feseliud.item.block.icecream.*;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.AbstractBlock;

import java.util.Map;

public class ModBlocks {
    public static final IceCreamBarMoldBlock ICE_CREAM_BAR_MOLD_BLOCK = register(new IceCreamBarMoldBlock(AbstractBlock.Settings.create().nonOpaque(), "ice_cream_bar_mold"));
    public static final MixerBlock MIXER_BLOCK = register(new MixerBlock(AbstractBlock.Settings.create().nonOpaque(), "mixer"));
    public static final CoolerBoxBlock COOLER_BOX_BLOCK = register(new CoolerBoxBlock(AbstractBlock.Settings.create().strength(1.5f).nonOpaque(), "cooler_box"));
    public static final PlateBlock PLATE_BLOCK = register(new PlateBlock(AbstractBlock.Settings.create().strength(1f).nonOpaque(), "plate"));
    public static final IceCreamMachineBlock ICE_CREAM_MACHINE_BLOCK = register(new IceCreamMachineBlock(AbstractBlock.Settings.create().strength(1.5f).nonOpaque(), "ice_cream_machine"));
    public static final BaseLiquidFluidBlock MIKI_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.MILK_FLUIDS));

    public static final BaseLiquidFluidBlock MILK_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.MILK_ICE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockItemLevel, IceCreamBlock> ICE_CREAM_BLOCKS = RegisterUtil.registerLevelBlocks(IceCream.IceCreamLevelsItem.values(),
            level -> new IceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock CHOCOLATE_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHOCOLATE_LIQUID_FLUIDS));
    public static final Map<Chocolate.ChocolateLevels, ChocolateBlock> CHOCOLATE_BLOCKS = RegisterUtil.registerLevelBlocks(Chocolate.ChocolateLevels.values(),
            level -> new ChocolateBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock CHOCOLATE_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHOCOLATE_ICE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockItemLevel, IceCreamBlock> CHOCOLATE_ICE_CREAM_BLOCKS = RegisterUtil.registerLevelBlocks(ChocolateIceCream.IceCreamLevelsItem.values(),
            level -> new ChocolateIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock COOKIE_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.COOKIE_ICE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockItemLevel, IceCreamBlock> COOKIE_ICE_CREAM_BLOCKS = RegisterUtil.registerLevelBlocks(CookieIceCream.IceCreamLevelsItem.values(),
            level -> new CookieIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock GOLDEN_APPLE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.GOLDEN_APPLE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockItemLevel, IceCreamBlock> GOLDEN_APPLE_ICE_CREAM_BLOCKS = RegisterUtil.registerLevelBlocks(GoldenAppleIceCream.IceCreamLevelsItem.values(),
            level -> new GoldenAppleIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock CHORUS_FRUIT_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHORUS_FRUIT_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockItemLevel, IceCreamBlock> CHORUS_FRUIT_ICE_CREAM_BLOCKS = RegisterUtil.registerLevelBlocks(ChorusFruitIceCream.IceCreamLevelsItem.values(),
            level -> new ChorusFruitIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock GLOW_BERRIES_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.GLOW_BERRIES_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockItemLevel, IceCreamBlock> GLOW_BERRIES_ICE_CREAM_BLOCKS = RegisterUtil.registerLevelBlocks(GlowBerriesIceCream.IceCreamLevelsItem.values(),
            level -> new GlowBerriesIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock SALT_WATER_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.SALT_WATER_FLUIDS));
    public static final IncompleteIce INCOMPLETE_ICE_STAIRS_BLOCK = register(new IncompleteIce("incomplete_ice_stairs", true));
    public static final IncompleteIce INCOMPLETE_ICE_SLAB_BLOCK = register(new IncompleteIce("incomplete_ice_slab", false));


    public static <IB extends IModBlock> IB register(IB block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
