package fengliu.feseliud.mixin;


import fengliu.feseliud.block.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class MixinBucketItem extends Item{

    public MixinBucketItem(Settings settings) {
        super(settings);
    }

    @Inject(
            method = "use",
            at = @At(
                    "HEAD"
            ),
            cancellable = true
    )
    public void useOnBlock(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.WATER);
        if (world.getBlockState(blockHitResult.getBlockPos()).isOf(ModBlocks.MIXER_BLOCK)){
            cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
            cir.cancel();
        }
    }
}
