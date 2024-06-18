package fengliu.feseliud.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class FacingBlock extends Block implements IModBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private final String name;

    public FacingBlock(Settings settings, String name) {
        super(settings);
        this.name = name;
    }

    /**
     * 获取方向形状
     * @param state 方块形状
     * @param facingShapes 方向方块形状组 北 -> 南 -> 西 -> 东
     * @return 方向方块形状
     */
    public static VoxelShape getFacingShape(BlockState state, VoxelShape... facingShapes){
        switch (state.get(FacingEntityBlock.FACING)) {
            case NORTH -> {
                return facingShapes[0];
            }
            case SOUTH -> {
                return facingShapes[1];
            }
            case WEST -> {
                return facingShapes[2];
            }
        }
        return facingShapes[3];
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public String getBlockName() {
        return this.name;
    }
}
