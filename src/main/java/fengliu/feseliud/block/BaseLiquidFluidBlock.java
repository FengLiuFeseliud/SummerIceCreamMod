package fengliu.feseliud.block;

import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

public class BaseLiquidFluidBlock extends FluidBlock implements IModBlock{
    public final String name;

    public BaseLiquidFluidBlock(List<? extends BaseFluid> fluids, Settings settings) {
        super(fluids.get(RegisterUtil.FluidType.STILL.ordinal()), settings);
        this.name = fluids.get(RegisterUtil.FluidType.STILL.ordinal()).getName();
    }

    public BaseLiquidFluidBlock(List<? extends BaseFluid> fluids) {
        this(fluids, Settings.copy(Blocks.WATER));
    }

    @Override
    public String getBlockName() {
        return this.name;
    }

    @Override
    public String getTextureName() {
        return this.name;
    }

    @Override
    public void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        Models.PARTICLE.upload(this,  TextureMap.particle(this.getModelId()), blockStateModelGenerator.modelCollector);
    }
}
