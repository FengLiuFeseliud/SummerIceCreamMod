package fengliu.feseliud.fluid.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.world.WorldView;

public class MilkFluid extends BaseFluid {
    public static final String name = "milk_fluid";

    @Override
    public Block getIceBlock() {
        return null;
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 10;
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.MILK_FLUIDS.get(RegisterUtil.FluidType.FLOWING.ordinal());
    }

    @Override
    public Fluid getStill() {
        return ModFluids.MILK_FLUIDS.get(RegisterUtil.FluidType.FLOWING.ordinal());
    }

    @Override
    public Item getBucketItem() {
        return Items.MILK_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModBlocks.MIKI_BLOCK.getDefaultState().with(Properties.LEVEL_15, FlowableFluid.getBlockStateLevel(fluidState));
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
        return MilkFluid.name;
    }

    @Override
    public int getFluidColor() {
        return 0xF9FFFE;
    }

    public static class Flowing extends MilkFluid {
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

    public static class Still extends MilkFluid {
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
