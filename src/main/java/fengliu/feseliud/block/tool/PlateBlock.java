package fengliu.feseliud.block.tool;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.IInventoryBlock;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.block.entity.PlateBlockEntity;
import fengliu.feseliud.item.block.IInventoryItem;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.brick.IceCreamBrick;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
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

public class PlateBlock extends FacingEntityBlock implements IInventoryBlock {
    public static final VoxelShape SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.14, 1);

    public PlateBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.PLATE_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return PlateBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlateBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public ItemStack getBlockItemStack() {
        return ModBlockItems.PLATE.getDefaultStack();
    }

    @Override
    public ItemStack setBlockItemStack(ItemStack stack) {
        stack.setDamage(PlateBlockEntity.SIZE - ((IInventoryItem) stack.getItem()).size(stack));
        return stack;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        List<ItemStack> stacks = this.setNbtToDroppedStacks(super.getDroppedStacks(state, builder), builder);
        ItemStack stack = stacks.get(0);
        stack.setDamage(PlateBlockEntity.SIZE - ((IInventoryItem) stack.getItem()).size(stack));
        return stacks;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient() || Hand.OFF_HAND.equals(hand)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (this.setItemStackToHand(world, pos, player, hand)){
            return ActionResult.SUCCESS;
        }

        if (!(world.getBlockEntity(pos) instanceof PlateBlockEntity be)){
            return ActionResult.SUCCESS;
        }

        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isEmpty()){
            be.takeItem().finishUsing(world, player);
        } else if (handStack.getItem() instanceof IceCreamBrick){
            if (be.putItem(handStack) && !player.isCreative()){
                handStack.decrement(1);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
    }
}
