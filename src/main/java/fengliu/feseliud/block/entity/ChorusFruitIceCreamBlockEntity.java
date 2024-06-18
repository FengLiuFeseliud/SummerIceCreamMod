package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockItemLevel;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class ChorusFruitIceCreamBlockEntity extends IceCreamBlockEntity{
    public ChorusFruitIceCreamBlockEntity(BlockPos pos, BlockState state) {
        super(getBlockEntitytype(ModBlockEntitys.CHORUS_FRUIT_ICE_CREAM_BLOCK_ENTITYS, state), pos, state);
    }

    @Override
    public Map<IIceCreamBlockItemLevel, IceCreamBlock> getIceCreamBlocks() {
        return ModBlocks.CHORUS_FRUIT_ICE_CREAM_BLOCKS;
    }
}
