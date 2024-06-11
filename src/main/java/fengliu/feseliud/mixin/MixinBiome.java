package fengliu.feseliud.mixin;

import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.fluid.BaseFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class MixinBiome{
    @Shadow
    public boolean doesNotSnow(BlockPos pos) { return false; }

    /**
     * 是否可以让 BaseFluid 结冰
     */
    @Inject(
            method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void canSetBaseFluidIce(WorldView world, BlockPos pos, boolean doWaterCheck, CallbackInfoReturnable<Boolean> cir){
        if (this.doesNotSnow(pos)) {
            return;
        }

        if (pos.getY() <= world.getBottomY() || pos.getY() > world.getTopY() || world.getLightLevel(LightType.BLOCK, pos) > 10){
            return;
        }

        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);

        if (!(fluidState.getFluid() instanceof BaseFluid baseFluid) || !(blockState.getBlock() instanceof FluidBlock)){
            return;
        }

        if (baseFluid.getIceBlock() == null || !baseFluid.isStill(fluidState)){
            return;
        }

        cir.setReturnValue(true);
        cir.cancel();
    }
}
