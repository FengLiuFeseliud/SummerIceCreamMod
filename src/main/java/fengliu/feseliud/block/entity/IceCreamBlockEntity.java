package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class IceCreamBlockEntity extends BlockEntity {
    private int tick = 0;

    public IceCreamBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public IceCreamBlockEntity(BlockPos pos, BlockState state) {
        super(getBlockEntitytype(ModBlockEntitys.ICE_CREAM_BLOCK_ENTITYS, state), pos, state);
    }

    public static <IE extends IceCreamBlockEntity> BlockEntityType<?> getBlockEntitytype(Map<IIceCreamBlockLevel, BlockEntityType<IE>> iceCreamBlockEntitys, BlockState state){
        return iceCreamBlockEntitys.get(((IceCreamBlock) state.getBlock()).getItemLevel());
    }

    public Map<IIceCreamBlockLevel, IceCreamBlock> getIceCreamBlocks(){
        return ModBlocks.ICE_CREAM_BLOCKS;
    }

    public int getTick() {
        return this.tick;
    }

    public void initTick(ItemStack iceCreamBlockStack){
        NbtCompound nbt = iceCreamBlockStack.getOrCreateNbt();
        if (nbt.contains(IIceCreamLevelItem.THAW_TIME_KEY, NbtElement.INT_TYPE)){
            this.tick = nbt.getInt(IIceCreamLevelItem.THAW_TIME_KEY);
            return;
        }

        this.tick = ((IIceCreamLevelItem) iceCreamBlockStack.getItem()).getThawTime();
    }

    public void initTick(IceCreamBlock block){
        if (this.tick == 0){
            this.tick = block.getItemLevel().getThawTime();
        }
    }

    public boolean canThaw(){
        this.tick --;
        this.markDirty();
        return tick  <= 0;
    }

    public BlockState getIceCreamThawBlockState(BlockState iceCreamBlockState){
        IceCreamBlock iceCreamBlock = (IceCreamBlock) iceCreamBlockState.getBlock();
        int level = iceCreamBlock.getItemLevel().getLevel();

        if (level >= iceCreamBlock.getItemLevel().getMaxLevel()){
            return Blocks.AIR.getDefaultState();
        }
        return ((IceCreamBlock) this.getIceCreamBlocks().values().toArray()[level]).getDefaultState().with(IceCreamBlock.FACING, iceCreamBlockState.get(IceCreamBlock.FACING));
    }

    public void thaw(World world, BlockPos pos, BlockState iceCreamBlockState){
        world.removeBlock(pos, true);
        world.removeBlockEntity(pos);

        world.setBlockState(pos, this.getIceCreamThawBlockState(iceCreamBlockState));
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!(blockEntity instanceof IceCreamBlockEntity be) || world.isClient()){
            return;
        }

        be.initTick((IceCreamBlock) state.getBlock());
        // 下方是冰或者当前生物群系下能结冰时不会化
        if (world.getBlockState(pos.down()).isOf(Blocks.ICE) || world.getBiome(pos).value().isCold(pos)){
            return;
        }

        if (be.canThaw()){
            be.thaw(world, pos, state);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(IIceCreamLevelItem.THAW_TIME_KEY, this.tick);
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.tick = nbt.getInt(IIceCreamLevelItem.THAW_TIME_KEY);
        this.markDirty();
    }
}
