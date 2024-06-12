package fengliu.feseliud.block.icecream;


import fengliu.feseliud.block.FacingBlock;
import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.IceCreamBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.data.generator.LootTablesGeneration;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class IceCreamBlock extends FacingEntityBlock {
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0, 0.5, 0.5, 1, 1,1));
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0, 0.5, 0, 1, 1,0.5));
    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0.5, 0.5, 0, 1, 1,1));
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0, 0.5, 0, 0.5, 1,1));
    public final IIceCreamBlockLevel iIceCreamBlockLevel;

    public IceCreamBlock(Settings settings, String name, IIceCreamBlockLevel iIceCreamBlockLevel) {
        super(settings, name);
        this.iIceCreamBlockLevel = iIceCreamBlockLevel;
    }

    public IIceCreamBlockLevel getIceCreamBlockLevel(){
        return this.iIceCreamBlockLevel;
    }

    public Map<IceCreamBar, IIceCreamLevel> getIceCreams(){
        return ModItems.ICE_CREAM_BRICKS;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.ICE_CREAM_BLOCK_ENTITYS.get(this.getIceCreamBlockLevel());
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return IceCreamBlockEntity::tick;
    }

    @Override
    public String getBlockName() {
        return this.getIceCreamBlockLevel().getIdName();
    }

    @Override
    public String getTextureName() {
        return this.getBlockName();
    }

    @Override
    public Identifier getTexturePath() {
        return ModBlocks.MILK_ICE_CREAM_LIQUID_BLOCK.getTexturePath();
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IceCreamBlockEntity(pos, state);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!EnchantmentHelper.fromNbt(tool.getEnchantments()).containsKey(Enchantments.SILK_TOUCH) || !tool.isIn(ItemTags.SHOVELS)){
            super.afterBreak(world, player, pos, state, blockEntity, tool);
            return;
        }

        if (!(world instanceof ServerWorld serverWorld) || !(blockEntity instanceof IceCreamBlockEntity iceCreamBlockEntity)) {
            super.afterBreak(world, player, pos, state, blockEntity, tool);
            return;
        }

        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005f);

        ItemStack stack = this.asItem().getDefaultStack();
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt(IIceCreamLevelItem.THAW_TIME_KEY, iceCreamBlockEntity.getTick());

        Block.dropStack(world, pos, stack);
        state.onStacksDropped(serverWorld, pos, tool, true);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient()){
            super.onPlaced(world, pos, state, placer, itemStack);
        }

        if (world.getBlockEntity(pos) instanceof IceCreamBlockEntity iceCreamBlockEntity){
            iceCreamBlockEntity.initTick(itemStack);
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    protected VoxelShape getShape(BlockState state){
        int iIceCreamBlockLevel = this.getIceCreamBlockLevel().getLevel();
        if (iIceCreamBlockLevel == 1){
            return VoxelShapes.cuboid(0, 0, 0, 1, 1,1);
        } else if (iIceCreamBlockLevel == 2){
            return FacingBlock.getFacingShape(state, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE);
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
    public void generateLootItem(LootTablesGeneration lootTablesGeneration) {
        int level = 5 - this.getIceCreamBlockLevel().getLevel();
        lootTablesGeneration.addDrop(this, LootTable.builder().pool(LootPool.builder()
                .with(ItemEntry.builder((Item) this.getIceCreams().keySet().toArray()[0])
                        .apply((SetCountLootFunction.builder(ConstantLootNumberProvider.create(level)))))
        ));
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        int level = this.getIceCreamBlockLevel().getLevel();
        Identifier pathId = this.getTexturePath();

        if (level == 1){
            Models.CUBE_ALL.upload(this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        } else if (level == 2){
            Models.STAIRS.upload(this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        } else if (level == 3){
            Models.SLAB.upload(this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        }
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        if (this.getIceCreamBlockLevel().getLevel() == 2){
            Identifier modelId = this.getModelId();
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(this)
                    .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                            .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
                            .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                            .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                            .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                    )
            );
            return;
        }
        super.generateBlockStateModel(blockStateModelGenerator);
    }
}
