package fengliu.feseliud.block.entity;

import fengliu.feseliud.fluid.ModFluids;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MixerBlockEntity extends InventoryBlockEntity {
    public FluidState fluidState;

    public MixerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.MIXER_BLOCK_ENTITY, pos, state);
        this.setMaxItemStack(1);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity be) {

    }

    @Override
    public Text getName() {
        return null;
    }
}
