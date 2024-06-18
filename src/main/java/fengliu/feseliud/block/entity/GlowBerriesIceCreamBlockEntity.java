package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockItemLevel;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class GlowBerriesIceCreamBlockEntity extends IceCreamBlockEntity{
    public GlowBerriesIceCreamBlockEntity(BlockPos pos, BlockState state) {
        super(getBlockEntitytype(ModBlockEntitys.GLOW_BERRIES_CREAM_BLOCK_ENTITYS, state), pos, state);
    }

    @Override
    public Map<IIceCreamBlockItemLevel, IceCreamBlock> getIceCreamBlocks() {
        return ModBlocks.GLOW_BERRIES_ICE_CREAM_BLOCKS;
    }
}
