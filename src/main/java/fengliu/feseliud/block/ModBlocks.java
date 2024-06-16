package fengliu.feseliud.block;

import fengliu.feseliud.block.icecream.*;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.block.Chocolate;
import fengliu.feseliud.item.block.icecream.*;
import fengliu.feseliud.utils.RegisterUtil;
import fengliu.feseliud.utils.level.ILevel;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

import java.util.Map;

public class ModBlocks {
    public static final IceCreamBarMoldBlock ICE_CREAM_BAR_MOLD_BLOCK = register(new IceCreamBarMoldBlock(AbstractBlock.Settings.create().nonOpaque(), "ice_cream_bar_mold"));
    public static final MixerBlock MIXER_BLOCK = register(new MixerBlock(AbstractBlock.Settings.create().nonOpaque(), "mixer"));
    public static final CoolerBoxBlock COOLER_BOX_BLOCK = register(new CoolerBoxBlock(AbstractBlock.Settings.create().strength(1.5f).nonOpaque(), "cooler_box"));
    public static final BaseLiquidFluidBlock MIKI_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.MILK_FLUIDS));

    public static final BaseLiquidFluidBlock MILK_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.MILK_ICE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockLevel, IceCreamBlock> ICE_CREAM_BLOCKS = RegisterUtil.registerDetachableBlocks(IceCream.IceCreamLevels.values(),
            level -> new IceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock CHOCOLATE_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHOCOLATE_LIQUID_FLUIDS));
    public static final Map<Chocolate.ChocolateLevels, ChocolateBlock> CHOCOLATE_BLOCKS = RegisterUtil.registerDetachableBlocks(Chocolate.ChocolateLevels.values(),
            level -> new ChocolateBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock CHOCOLATE_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHOCOLATE_ICE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockLevel, IceCreamBlock> CHOCOLATE_ICE_CREAM_BLOCKS = RegisterUtil.registerDetachableBlocks(ChocolateIceCream.IceCreamLevels.values(),
            level -> new ChocolateIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock COOKIE_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.COOKIE_ICE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockLevel, IceCreamBlock> COOKIE_ICE_CREAM_BLOCKS = RegisterUtil.registerDetachableBlocks(CookieIceCream.IceCreamLevels.values(),
            level -> new CookieIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock GOLDEN_APPLE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.GOLDEN_APPLE_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockLevel, IceCreamBlock> GOLDEN_APPLE_ICE_CREAM_BLOCKS = RegisterUtil.registerDetachableBlocks(GoldenAppleIceCream.IceCreamLevels.values(),
            level -> new GoldenAppleIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock CHORUS_FRUIT_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHORUS_FRUIT_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockLevel, IceCreamBlock> CHORUS_FRUIT_ICE_CREAM_BLOCKS = RegisterUtil.registerDetachableBlocks(ChorusFruitIceCream.IceCreamLevels.values(),
            level -> new ChorusFruitIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock GLOW_BERRIES_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.GLOW_BERRIES_CREAM_LIQUID_FLUIDS));
    public static final Map<IIceCreamBlockLevel, IceCreamBlock> GLOW_BERRIES_ICE_CREAM_BLOCKS = RegisterUtil.registerDetachableBlocks(GlowBerriesIceCream.IceCreamLevels.values(),
            level -> new GlowBerriesIceCreamBlock(AbstractBlock.Settings.create().nonOpaque(), level.getName(), level));

    public static final BaseLiquidFluidBlock SALT_WATER_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.SALT_WATER_FLUIDS));
    public static final IncompleteIce INCOMPLETE_ICE_STAIRS_BLOCK = register(new IncompleteIce("incomplete_ice_stairs", true));
    public static final IncompleteIce INCOMPLETE_ICE_SLAB_BLOCK = register(new IncompleteIce("incomplete_ice_slab", false));


    public static <B extends Block, IB extends IModBlock> B register(IB block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
