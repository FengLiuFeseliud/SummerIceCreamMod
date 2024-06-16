package fengliu.feseliud.block.icecream;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.ChorusFruitIceCreamBlockEntity;
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

public class ChorusFruitIceCreamBlock extends IceCreamBlock{
    public ChorusFruitIceCreamBlock(Settings settings, String name, IIceCreamBlockLevel iIceCreamBlockLevel) {
        super(settings, name, iIceCreamBlockLevel);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelBrickItems() {
        return ModItems.CHORUS_FRUIT_ICE_CREAM_BRICKS;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.CHORUS_FRUIT_ICE_CREAM_BLOCK_ENTITYS.get(this.getItemLevel());
    }

    @Override
    public Identifier getTexturePath() {
        return ModBlocks.CHORUS_FRUIT_CREAM_LIQUID_BLOCK.getTexturePath();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChorusFruitIceCreamBlockEntity(pos, state);
    }
}
