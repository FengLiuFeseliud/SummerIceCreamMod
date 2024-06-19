package fengliu.feseliud.block.tool;

import fengliu.feseliud.block.FacingBlock;
import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.utils.HitSlot;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class IceCreamBarMoldBlock extends FacingEntityBlock {
    public static final IHitSlot[] HIT_SLOTS = HitSlot.ThreeHitSlot.values();

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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient() || hand == Hand.OFF_HAND){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        IHitSlot hitSlots = IHitSlot.getHitSlot(state, hit, HIT_SLOTS);
        if (hitSlots == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        IceCreamBarMoldBlockEntity be = (IceCreamBarMoldBlockEntity) world.getBlockEntity(pos);
        if (be == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isOf(Items.BUCKET) || handStack.isEmpty()){
            player.setStackInHand(hand, ItemUsage.exchangeStack(handStack, player, be.takeItem(hitSlots, handStack)));
        } else {
            ItemStack stack = be.putItem(hitSlots, handStack);
            if (!player.isCreative() && !stack.isOf(Items.BUCKET)){
                stack.decrement(1);
            }
            player.setStackInHand(hand, stack);
        }
        return ActionResult.SUCCESS;
    }
}
