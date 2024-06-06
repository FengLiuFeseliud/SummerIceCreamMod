package fengliu.feseliud.block.icecream;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
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
        return FacingEntityBlock.getFacingShape(state, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FacingEntityBlock.getFacingShape(state, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE);
    }

    @Override
    public String getTextureName() {
        return this.name;
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        return;
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        return;
    }

    public enum HitSlots implements IHitSlot {
        SLOT1(0, 93, 73, 71, 1),
        SLOT2(1, 60, 72, 40, 1),
        SLOT3(2, 27, 71, 8, 1);

        public final int index;
        public final int x1;
        public final int z1;
        public final int x2;
        public final int z2;

        HitSlots(int index, int x1, int z1, int x2, int z2){
            this.index = index;
            this.x1 = x1;
            this.z1 = z1;
            this.x2 = x2;
            this.z2 = z2;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        @Override
        public int getX1() {
            return this.x1;
        }

        @Override
        public int getX2() {
            return this.x2;
        }

        @Override
        public int getZ1() {
            return this.z1;
        }

        @Override
        public int getZ2() {
            return this.z2;
        }
    }

    public enum HitSlots2 implements IHitSlot {
        SLOT1(2, 93, 73, 71, 1),
        SLOT2(1, 60, 72, 40, 1),
        SLOT3(0, 27, 71, 8, 1);

        public final int index;
        public final int x1;
        public final int z1;
        public final int x2;
        public final int z2;

        HitSlots2(int index, int x1, int z1, int x2, int z2){
            this.index = index;
            this.x1 = x1;
            this.z1 = z1;
            this.x2 = x2;
            this.z2 = z2;
        }

        @Override
        public int getIndex() {
            return this.index;
        }

        @Override
        public int getX1() {
            return this.x1;
        }

        @Override
        public int getX2() {
            return this.x2;
        }

        @Override
        public int getZ1() {
            return this.z1;
        }

        @Override
        public int getZ2() {
            return this.z2;
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient() || hand == Hand.OFF_HAND){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        IHitSlot hitSlots = IHitSlot.getHitSlot(hit, state, HitSlots.values(), HitSlots2.values());
        if (hitSlots == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        IceCreamBarMoldBlockEntity be = (IceCreamBarMoldBlockEntity) world.getBlockEntity(pos);
        if (be == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack handStack = player.getStackInHand(hand);
        if (player.isSneaking() || handStack.isOf(Items.BUCKET)){
            be.takeItem(player, hitSlots, world, pos, handStack);
            return ActionResult.SUCCESS;
        }

        if (handStack.isOf(Items.AIR)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        be.putItem(player, hitSlots, handStack);
        return ActionResult.SUCCESS;
    }
}
