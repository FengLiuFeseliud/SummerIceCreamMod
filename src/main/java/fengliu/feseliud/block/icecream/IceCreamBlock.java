package fengliu.feseliud.block.icecream;


import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.IDetachableBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.IceCreamBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockItemLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;


public class IceCreamBlock extends FacingEntityBlock implements IDetachableBlock {
    public final IIceCreamBlockItemLevel iIceCreamBlockItemLevel;

    public IceCreamBlock(Settings settings, String name, IIceCreamBlockItemLevel iIceCreamBlockItemLevel) {
        super(settings, name);
        this.iIceCreamBlockItemLevel = iIceCreamBlockItemLevel;
    }

    @Override
    public IIceCreamBlockItemLevel getItemLevel(){
        return this.iIceCreamBlockItemLevel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<IceCreamBar, IIceCreamLevel> getLevelBrickItems(){
        return ModItems.ICE_CREAM_BRICKS;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.ICE_CREAM_BLOCK_ENTITYS.get(this.getItemLevel());
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return IceCreamBlockEntity::tick;
    }

    @Override
    public String getBlockName() {
        return this.getItemLevel().getIdName();
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
        nbt.putInt(IIceCreamLevelItem.THAW_TICK_KEY, iceCreamBlockEntity.getTick());

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

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    @Override
    public void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        IDetachableBlock.super.generateBlockStateModel(blockStateModelGenerator);
    }
}
