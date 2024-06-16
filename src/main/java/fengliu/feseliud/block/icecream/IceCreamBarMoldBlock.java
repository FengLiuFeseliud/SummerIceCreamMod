package fengliu.feseliud.block.icecream;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.FacingBlock;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.data.generator.LootTablesGeneration;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;


public class IceCreamBarMoldBlock extends FacingEntityBlock {
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.19, 0.81);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.cuboid(0, 0, 0.19, 1, 0.19, 1);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.cuboid(0, 0, 0, 0.81, 0.19, 1);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.cuboid(0.19, 0, 0, 1, 0.19, 1);

    public IceCreamBarMoldBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.ICE_CREAM_BAR_MOLD_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return IceCreamBarMoldBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IceCreamBarMoldBlockEntity(pos, state);
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
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient() || hand == Hand.OFF_HAND){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        IHitSlot hitSlots = IHitSlot.getHitSlot(state, hit, IHitSlot.ThreeHitSlot.values());
        if (hitSlots == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        IceCreamBarMoldBlockEntity be = (IceCreamBarMoldBlockEntity) world.getBlockEntity(pos);
        if (be == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isOf(Items.BUCKET) || handStack.isEmpty()){
            ItemStack stack = be.takeItem(player, hand, hitSlots, world, pos, handStack);
            if (handStack.isEmpty()){
                player.setStackInHand(hand, stack);
            } else {
                dropStack(world, pos, stack);
            }
            return ActionResult.SUCCESS;
        }

        ItemStack stack = be.putItem(player, hitSlots, handStack);
        if (handStack.isEmpty()){
            player.setStackInHand(hand, stack);
        } else {
            dropStack(world, pos, stack);
        }
        return ActionResult.SUCCESS;
    }
}
