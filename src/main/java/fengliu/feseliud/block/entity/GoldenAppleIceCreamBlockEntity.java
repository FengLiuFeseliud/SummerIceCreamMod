package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class GoldenAppleIceCreamBlockEntity extends IceCreamBlockEntity{

    public GoldenAppleIceCreamBlockEntity(BlockPos pos, BlockState state) {
        super(getBlockEntitytype(ModBlockEntitys.GOLDEN_APPLE_ICE_CREAM_BLOCK_ENTITYS, state), pos, state);
    }

    @Override
    public Map<IIceCreamBlockLevel, IceCreamBlock> getIceCreamBlocks() {
        return ModBlocks.GOLDEN_APPLE_ICE_CREAM_BLOCKS;
    }
}
