package fengliu.feseliud.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public abstract class FacingEntityBlock extends BlockWithEntity implements IModBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public final String name;

    public FacingEntityBlock(Settings settings, String name) {
        super(settings);
        this.name = name;
    }

    public abstract BlockEntityType<?> getBlockEntityType();
    public abstract BlockEntityTicker<? super BlockEntity> uesTick();

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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, this.getBlockEntityType(), this.uesTick());
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