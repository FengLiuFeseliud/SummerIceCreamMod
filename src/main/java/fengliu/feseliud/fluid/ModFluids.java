package fengliu.feseliud.fluid;

import fengliu.feseliud.fluid.icecream.*;
import fengliu.feseliud.utils.RegisterUtil;

import java.util.List;

public class ModFluids{
    public static final List<MilkIceCreamLiquidFluid> MILK_ICE_CREAM_LIQUID_FLUIDS = RegisterUtil.registerFluids(new MilkIceCreamLiquidFluid.Still(), new MilkIceCreamLiquidFluid.Flowing());
    public static final List<MilkFluid> MILK_FLUIDS= RegisterUtil.registerFluids(new MilkFluid.Still(), new MilkFluid.Flowing());
    public static final List<CookieIceCreamLiquidFluid> COOKIE_ICE_CREAM_LIQUID_FLUIDS = RegisterUtil.registerFluids(new CookieIceCreamLiquidFluid.Still(), new CookieIceCreamLiquidFluid.Flowing());
    public static final List<GoldenAppleIceCreamLiquidFluid> GOLDEN_APPLE_CREAM_LIQUID_FLUIDS = RegisterUtil.registerFluids(new GoldenAppleIceCreamLiquidFluid.Still(), new GoldenAppleIceCreamLiquidFluid.Flowing());
    public static final List<ChorusFruitIceCreamLiquidFluid> CHORUS_FRUIT_CREAM_LIQUID_FLUIDS = RegisterUtil.registerFluids(new ChorusFruitIceCreamLiquidFluid.Still(), new ChorusFruitIceCreamLiquidFluid.Flowing());
    public static final List<GlowBerriesIceCreamLiquidFluid> GLOW_BERRIES_CREAM_LIQUID_FLUIDS = RegisterUtil.registerFluids(new GlowBerriesIceCreamLiquidFluid.Still(), new GlowBerriesIceCreamLiquidFluid.Flowing());
    public static final List<SaltWaterFluid> SALT_WATER_FLUIDS= RegisterUtil.registerFluids(new SaltWaterFluid.Still(), new SaltWaterFluid.Flowing());

    public static void registerAllFluids(){

    }
}
