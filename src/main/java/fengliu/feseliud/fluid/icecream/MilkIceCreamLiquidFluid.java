package fengliu.feseliud.fluid.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.icecream.IceCream;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public class MilkIceCreamLiquidFluid extends BaseFluid {
    public static final String name = "milk_ice_cream_liquid_fluid";

    @Override
    public MilkIceCreamLiquidFluid getFlowing() {
        return ModFluids.MILK_ICE_CREAM_LIQUID_FLUIDS.get(RegisterUtil.FluidType.FLOWING.ordinal());
    }

    @Override
    public MilkIceCreamLiquidFluid getStill() {
        return ModFluids.MILK_ICE_CREAM_LIQUID_FLUIDS.get(RegisterUtil.FluidType.STILL.ordinal());
    }

    @Override
    public Item getBucketItem() {
        return ModItems.MILK_ICE_CREAM_LIQUID_BUCKET;
    }

    @Override
    public Block getIceBlock() {
        return ModBlocks.ICE_CREAM_BLOCKS.get(IceCream.IceCreamLevels.NOT_THAW);
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.MILK_ICE_CREAM_LIQUID_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 2;
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    @Override
    public String getName() {
        return MilkIceCreamLiquidFluid.name;
    }

    public static class Flowing extends MilkIceCreamLiquidFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends MilkIceCreamLiquidFluid {
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
