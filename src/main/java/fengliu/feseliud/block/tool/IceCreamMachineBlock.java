package fengliu.feseliud.block.tool;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.ITranslucent;
import fengliu.feseliud.block.entity.IceCreamMachineBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.icecream.liquid.FoodLiquidBucket;
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

public class IceCreamMachineBlock extends FacingEntityBlock implements ITranslucent {
    public static final IHitSlot[] HIT_SLOTS = HitSlot.TwoHitSlot.values();

    public static final VoxelShape SHAPE = VoxelShapes.cuboid(0.12, 0, 0.12, 0.88,1, 0.88);
    public IceCreamMachineBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.ICE_CREAM_MACHINE_BLOCK_BLOCK;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return IceCreamMachineBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IceCreamMachineBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient() || hand == Hand.OFF_HAND){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (!(world.getBlockEntity(pos) instanceof IceCreamMachineBlockEntity be)){
            return ActionResult.FAIL;
        }

        ItemStack handStack = player.getStackInHand(hand);
        IHitSlot hitSlot = IHitSlot.getHitSlot(state, hit, HitSlot.TwoHitSlot.values());
        if (handStack.isEmpty() || handStack.isOf(Items.BUCKET)){
            ItemStack takeStack = be.takeItem(handStack, hitSlot);
            if (takeStack.isEmpty()){
                return ActionResult.PASS;
            }

            if (takeStack.getItem() instanceof FoodLiquidBucket){
                player.setStackInHand(hand, ItemUsage.exchangeStack(handStack, player, takeStack));
            } else {
                player.setStackInHand(hand, takeStack);
            }
        } else {
            ItemStack stack = be.putItem(handStack, hitSlot);
            if (stack.isEmpty()){
                return ActionResult.PASS;
            } else if (!player.isCreative()){
                player.setStackInHand(hand, stack);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        return;
    }

}
