package fengliu.feseliud.block;

import fengliu.feseliud.block.icecream.IceCreamBarMoldBlock;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

public class ModBlocks {
    public static final IceCreamBarMoldBlock ICE_CREAM_BAR_MOLD_BLOCK = register(new IceCreamBarMoldBlock(AbstractBlock.Settings.create().nonOpaque(), "ice_cream_bar_mold"));
    public static final BaseLiquidFluidBlock MIKI_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.MILK_FLUIDS));
    public static final BaseLiquidFluidBlock MILK_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.MILK_ICE_CREAM_LIQUID_FLUIDS));
    public static final BaseLiquidFluidBlock COOKIE_ICE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.COOKIE_ICE_CREAM_LIQUID_FLUIDS));
    public static final BaseLiquidFluidBlock GOLDEN_APPLE_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.GOLDEN_APPLE_CREAM_LIQUID_FLUIDS));
    public static final BaseLiquidFluidBlock CHORUS_FRUIT_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.CHORUS_FRUIT_CREAM_LIQUID_FLUIDS));
    public static final BaseLiquidFluidBlock GLOW_BERRIES_CREAM_LIQUID_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.GLOW_BERRIES_CREAM_LIQUID_FLUIDS));
    public static final BaseLiquidFluidBlock SALT_WATER_BLOCK = register(new BaseLiquidFluidBlock(ModFluids.SALT_WATER_FLUIDS));


    public static <B extends Block, IB extends IModBlock> B register(IB block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
