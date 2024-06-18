package fengliu.feseliud.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

/**
 * 简单液体
 */
public abstract class BaseFluid extends FlowableFluid  {
    String PREFIXED_PATH = "fluid/";

    /**
     * 可凝固成的方块
     * @return 凝固方块
     */
    public abstract Block getCongealBlock();

    /**
     * 是否可以凝固
     */
    public boolean canCongeal(WorldView world, BlockPos pos, Biome biome, FluidState fluidState){
        return false;
    }

    /**
     * 液体颜色
     * @return 16位液体颜色
     */
    public int getFluidColor(){
        return -1;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    /**
     * 液体是否可以形成无限液体
     * @param world 世界
     * @return true 可以形成
     */
    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    @Override
    public int getTickRate(WorldView world) {
        return 15;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    public String getTextureNamePath(String fluidType){
        return PREFIXED_PATH + this.getName() + "/" + fluidType;
    }

    public abstract String getName();
}
