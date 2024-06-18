package fengliu.feseliud.block.tool;

import fengliu.feseliud.block.FacingBlock;
import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.IInventoryBlock;
import fengliu.feseliud.block.entity.CoolerBoxBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.IIceCreamPack;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoolerBoxBlock extends FacingEntityBlock implements IInventoryBlock {
    public static final VoxelShape WEST_SHAPE = VoxelShapes.cuboid(0.12, 0, 0.05, 0.88, 0.57, 0.95);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.cuboid(0.12, 0, 0.05, 0.88, 0.57, 0.95);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.cuboid(0.05, 0, 0.12, 0.95, 0.57, 0.88);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.cuboid(0.05, 0, 0.12, 0.95, 0.57, 0.88);
    public static final BooleanProperty CLOSE = BooleanProperty.of("close");

    public CoolerBoxBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.COOLER_BOX_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return CoolerBoxBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoolerBoxBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FacingBlock.getFacingShape(state, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FacingBlock.getFacingShape(state, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CLOSE);
    }

    @Override
    public ItemStack getBlockItemStack() {
        return ModBlockItems.COOLER_BOX.getDefaultStack();
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return this.setNbtToDroppedStacks(super.getDroppedStacks(state, builder), builder);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(CLOSE)){
            if (this.setItemStackToHand(world, pos, player, hand)){
                return ActionResult.SUCCESS;
            }
            world.setBlockState(pos, state.with(CLOSE, false));
            return ActionResult.SUCCESS;
        }

        if (player.isSneaking()){
            world.setBlockState(pos, state.with(CLOSE, true));
            return ActionResult.SUCCESS;
        }

        if (!(world.getBlockEntity(pos) instanceof CoolerBoxBlockEntity be)){
            return ActionResult.PASS;
        }

        ItemStack handStack = player.getStackInHand(hand);
        IHitSlot hitSlot = IHitSlot.getHitSlot(state, hit, IHitSlot.ThreeHitSlot.values());

        if (handStack.getItem() instanceof IIceCreamPack){
            be.putItem(handStack, hitSlot);
        } else if (handStack.isEmpty()){
            player.setStackInHand(hand, be.takeItem(hitSlot));
        }
        return ActionResult.SUCCESS;
    }

    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }
}
