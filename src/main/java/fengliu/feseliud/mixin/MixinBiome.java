package fengliu.feseliud.mixin;

import fengliu.feseliud.fluid.BaseFluid;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
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
     * 使 canSetIce 可以判断 BaseFluid 是否可以结冰
     */
    @Inject(
            method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void canCongealBaseFluid(WorldView world, BlockPos pos, boolean doWaterCheck, CallbackInfoReturnable<Boolean> cir){
        BlockState blockState = world.getBlockState(pos);
        if (!(blockState.getBlock() instanceof FluidBlock)){
            return;
        }

        FluidState fluidState = world.getFluidState(pos);
        if (!(fluidState.getFluid() instanceof BaseFluid baseFluid)){
            return;
        }

        cir.setReturnValue(baseFluid.canCongeal(world, pos, (Biome)(Object) this, fluidState));
        cir.cancel();
    }
}
