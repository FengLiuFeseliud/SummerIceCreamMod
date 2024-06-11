package fengliu.feseliud.block.icecream;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.ITranslucent;
import fengliu.feseliud.block.entity.MixerBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
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
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MixerBlock extends FacingEntityBlock implements ITranslucent {
    public static final BooleanProperty RUN = BooleanProperty.of("run");
    public final String name;

    public MixerBlock(Settings settings, String name) {
        super(settings, name);
        this.name = name;
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
    public String getTextureName() {
        return this.name;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        state.with(RUN, false);
        super.onPlaced(world, pos, state, placer, itemStack);
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
