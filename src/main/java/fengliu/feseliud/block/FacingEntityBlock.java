package fengliu.feseliud.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;


public abstract class FacingEntityBlock extends BaseEntityBlock implements IModBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private final String name;

    public FacingEntityBlock(Settings settings, String name) {
        super(settings, name);
        this.name = name;
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
    public String getTextureName() {
        return this.name;
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier modelId = this.getModelId();
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(this)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                        .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
                )
        );
    }
}