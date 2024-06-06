package fengliu.feseliud.fluid.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public class GoldenAppleIceCreamLiquidFluid extends MilkIceCreamLiquidFluid{
    public static final String name = "golden_apple_ice_cream_liquid_fluid";

    @Override
    public MilkIceCreamLiquidFluid getFlowing() {
        return ModFluids.GOLDEN_APPLE_CREAM_LIQUID_FLUIDS.get(RegisterUtil.FluidType.FLOWING.ordinal());
    }

    @Override
    public MilkIceCreamLiquidFluid getStill() {
        return ModFluids.GOLDEN_APPLE_CREAM_LIQUID_FLUIDS.get(RegisterUtil.FluidType.STILL.ordinal());
    }

    @Override
    public Item getBucketItem() {
        return ModItems.GOLDEN_APPLE_ICE_CREAM_LIQUID_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.GOLDEN_APPLE_CREAM_LIQUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, FlowableFluid.getBlockStateLevel(fluidState));
    }

    @Override
    public String getName() {
        return GoldenAppleIceCreamLiquidFluid.name;
    }

    public static class Flowing extends GoldenAppleIceCreamLiquidFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(FlowableFluid.LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(FlowableFluid.LEVEL);
        }
    }

    public static class Still extends GoldenAppleIceCreamLiquidFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
