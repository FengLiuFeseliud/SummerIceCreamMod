package fengliu.feseliud.fluid.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.icecream.ChorusFruitIceCream;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public class ChorusFruitIceCreamLiquidFluid extends MilkIceCreamLiquidFluid{
    public static final String name = "chorus_fruit_ice_cream_liquid_fluid";

    @Override
    public MilkIceCreamLiquidFluid getFlowing() {
        return ModFluids.CHORUS_FRUIT_CREAM_LIQUID_FLUIDS.get(RegisterUtil.FluidType.FLOWING.ordinal());
    }

    @Override
    public MilkIceCreamLiquidFluid getStill() {
        return ModFluids.CHORUS_FRUIT_CREAM_LIQUID_FLUIDS.get(RegisterUtil.FluidType.STILL.ordinal());
    }

    @Override
    public Item getBucketItem() {
        return ModItems.CHORUS_FRUIT_ICE_CREAM_LIQUID_BUCKET;
    }

    @Override
    public Block getCongealBlock() {
        return ModBlocks.CHORUS_FRUIT_ICE_CREAM_BLOCKS.get(ChorusFruitIceCream.IceCreamLevels.NOT_THAW);
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.CHORUS_FRUIT_CREAM_LIQUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, FlowableFluid.getBlockStateLevel(fluidState));
    }

    @Override
    public String getName() {
        return ChorusFruitIceCreamLiquidFluid.name;
    }

    public static class Flowing extends ChorusFruitIceCreamLiquidFluid {
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

    public static class Still extends ChorusFruitIceCreamLiquidFluid {
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
