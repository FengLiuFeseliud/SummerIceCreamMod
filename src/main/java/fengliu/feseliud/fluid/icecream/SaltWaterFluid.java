package fengliu.feseliud.fluid.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public class SaltWaterFluid extends MilkIceCreamLiquidFluid{
    public static final String name = "salt_water_fluid";

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 10;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public MilkIceCreamLiquidFluid getFlowing() {
        return ModFluids.SALT_WATER_FLUIDS.get(RegisterUtil.FluidType.FLOWING.ordinal());
    }

    @Override
    public MilkIceCreamLiquidFluid getStill() {
        return ModFluids.SALT_WATER_FLUIDS.get(RegisterUtil.FluidType.STILL.ordinal());
    }

    @Override
    public Item getBucketItem() {
        return ModItems.SALT_WATER_BUCKET;
    }

    @Override
    public Block getIceBlock() {
        return null;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.SALT_WATER_BLOCK.getDefaultState().with(Properties.LEVEL_15, FlowableFluid.getBlockStateLevel(fluidState));
    }

    @Override
    public String getName() {
        return SaltWaterFluid.name;
    }

    public static class Flowing extends SaltWaterFluid {
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

    public static class Still extends SaltWaterFluid {
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
