package fengliu.feseliud.block.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.GoldenAppleIceCreamBlockEntity;
import fengliu.feseliud.block.entity.IceCreamBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GoldenAppleIceCreamBlock extends IceCreamBlock{
    public GoldenAppleIceCreamBlock(Settings settings, String name, IIceCreamBlockLevel iIceCreamBlockLevel) {
        super(settings, name, iIceCreamBlockLevel);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreams() {
        return ModItems.GOLDEN_APPLE_ICE_CREAM_BRICKS;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.GOLDEN_APPLE_ICE_CREAM_BLOCK_ENTITYS.get(this.getIceCreamBlockLevel());
    }

    @Override
    public Identifier getTexturePath() {
        return ModBlocks.GOLDEN_APPLE_CREAM_LIQUID_BLOCK.getTexturePath();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GoldenAppleIceCreamBlockEntity(pos, state);
    }
}
