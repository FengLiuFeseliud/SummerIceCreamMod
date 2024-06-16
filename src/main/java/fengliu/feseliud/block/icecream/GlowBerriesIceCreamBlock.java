package fengliu.feseliud.block.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.GlowBerriesIceCreamBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GlowBerriesIceCreamBlock extends IceCreamBlock{
    public GlowBerriesIceCreamBlock(Settings settings, String name, IIceCreamBlockLevel iIceCreamBlockLevel) {
        super(settings, name, iIceCreamBlockLevel);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelBrickItems() {
        return ModItems.GLOW_BERRIES_ICE_CREAM_BRICK;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.GLOW_BERRIES_CREAM_BLOCK_ENTITYS.get(this.getItemLevel());
    }

    @Override
    public Identifier getTexturePath() {
        return ModBlocks.GLOW_BERRIES_CREAM_LIQUID_BLOCK.getTexturePath();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GlowBerriesIceCreamBlockEntity(pos, state);
    }
}
