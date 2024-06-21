package fengliu.feseliud.block.tool;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.ITranslucent;
import fengliu.feseliud.block.entity.IceCrusherBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IceCrusherBlock extends FacingEntityBlock implements ITranslucent {
    public static final BooleanProperty CLOSE = BooleanProperty.of("close");
    public static final IntProperty USE = IntProperty.of("use", 0, 3);

    public IceCrusherBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.ICE_CRUSHER_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return IceCrusherBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IceCrusherBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CLOSE);
        builder.add(USE);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.7, 0.75);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.7, 0.75);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (Hand.OFF_HAND.equals(hand) || !(world.getBlockEntity(pos) instanceof IceCrusherBlockEntity be)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (player.isSneaking()){
            if (!be.canCrusher() || !state.get(CLOSE)){
                world.setBlockState(pos, state.with(CLOSE, !state.get(CLOSE)).with(USE, 0));
            } else {
                world.setBlockState(pos, state.with(USE, state.get(USE) < 3 ? state.get(USE) + 1: 0));
                if (world.isClient()){
                    return ActionResult.SUCCESS;
                }
                be.crusher(world);
            }
            return ActionResult.SUCCESS;
        }

        if (world.isClient()){
            return ActionResult.PASS;
        }

        boolean close = state.get(CLOSE);
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isEmpty()){
            if (be.putItem(stack, player, close)){
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.FAIL;
            }
        }

        if (be.takeItem(player, hand, close)){
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
    }
}
