package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class ChocolateIceCreamBlockEntity extends IceCreamBlockEntity{
    public ChocolateIceCreamBlockEntity(BlockPos pos, BlockState state) {
        super(getBlockEntitytype(ModBlockEntitys.CHOCOLATE_ICE_CREAM_BLOCK_ENTITYS, state), pos, state);
    }

    @Override
    public Map<IIceCreamBlockLevel, IceCreamBlock> getIceCreamBlocks() {
        return ModBlocks.CHOCOLATE_ICE_CREAM_BLOCKS;
    }
}
