package fengliu.feseliud.block.icecream;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.ITranslucent;
import fengliu.feseliud.block.entity.MixerBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class MixerBlock extends FacingEntityBlock implements ITranslucent {
    public static final BooleanProperty RUN = BooleanProperty.of("run");
    public MixerBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.MIXER_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return MixerBlockEntity::tick;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        state.with(RUN, false);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient() || hand == Hand.OFF_HAND){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        MixerBlockEntity mixerBlockEntity = (MixerBlockEntity) world.getBlockEntity(pos);
        if (mixerBlockEntity == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack handStack = player.getStackInHand(hand);
        ItemStack baseStack = mixerBlockEntity.getBaseStack();

        if (handStack.isOf(Items.BUCKET) && !baseStack.isEmpty()){
            if (!player.isCreative()){
                dropStack(world, pos, baseStack);
                handStack.decrement(1);
            }
            mixerBlockEntity.setBaseStack(ItemStack.EMPTY);
            return ActionResult.SUCCESS;
        }

        if (player.isSneaking() && !baseStack.isEmpty() && !(baseStack.getItem() instanceof BucketItem || baseStack.isOf(Items.MILK_BUCKET))){
            dropStack(world, pos, baseStack);
            mixerBlockEntity.setBaseStack(ItemStack.EMPTY);
            return ActionResult.SUCCESS;
        }

        if (handStack.isEmpty() || !baseStack.isEmpty() || handStack.isOf(Items.BUCKET)){
            return ActionResult.FAIL;
        }

        if (!player.isCreative()){
            if (handStack.getItem() instanceof BucketItem || handStack.isOf(Items.MILK_BUCKET)){
                dropStack(world, pos, Items.BUCKET.getDefaultStack());
            }
            mixerBlockEntity.setBaseStack(handStack.split(1));
        } else {
            mixerBlockEntity.setBaseStack(handStack.copy().split(1));
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MixerBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RUN);
        super.appendProperties(builder);
    }

    protected static VoxelShape getShape(){
        return VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.13,1),
                VoxelShapes.cuboid(0, 0, 0, 0.13, 1,1),
                VoxelShapes.cuboid(0.87, 0, 0, 1, 1,1),
                VoxelShapes.cuboid(0, 0, 0, 1,1,0.13),
                VoxelShapes.cuboid(0, 0, 0.87, 1,1,1)
        );
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape();
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
    }

}
