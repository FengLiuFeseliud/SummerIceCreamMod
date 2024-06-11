package fengliu.feseliud.block;

import fengliu.feseliud.block.icecream.IceCreamBlock;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class IncompleteIce extends IceBlock implements IModBlock, ITranslucent {
    protected final boolean inStairs;
    protected final String name;
    public static final Identifier ICE_TEXTURE = new Identifier("minecraft", "ice").withPrefixedPath("block/");
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public IncompleteIce(String name, boolean inStairs) {
        super(AbstractBlock.Settings.create().mapColor(MapColor.PALE_PURPLE).slipperiness(0.98f).ticksRandomly().strength(0.5f).sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(Blocks::never));
        this.name = name;
        this.inStairs = inStairs;
    }

    @Override
    public String getBlockName() {
        return this.name;
    }

    @Override
    public String getTextureName() {
        return this.name;
    }

    public boolean inStairs(){
        return this.inStairs;
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
    public Identifier getTexturePath() {
        return ICE_TEXTURE;
    }

    protected VoxelShape getShape(BlockState state){
        if (this.inStairs()){
            return FacingBlock.getFacingShape(state, IceCreamBlock.NORTH_SHAPE, IceCreamBlock.SOUTH_SHAPE, IceCreamBlock.WEST_SHAPE, IceCreamBlock.EAST_SHAPE);
        }
        return VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state);
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier pathId = this.getTexturePath();
        if (this.inStairs()){
            Models.STAIRS.upload(this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        } else {
            Models.SLAB.upload(this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        }
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier modelId = this.getModelId();
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(this)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                        .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
                        .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                )
        );
    }
}
