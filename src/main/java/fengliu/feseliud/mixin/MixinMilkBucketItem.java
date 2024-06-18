package fengliu.feseliud.mixin;

import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.fluid.icecream.MilkFluid;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public abstract class MixinMilkBucketItem extends Item {

    public MixinMilkBucketItem(Settings settings) {
        super(settings);
    }

    /**
     * 使牛奶可以放置牛奶液体
     */
    @Inject(method = "use", at = @At("HEAD"))
    public void useMilkFluid(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.WATER);
        BlockPos hitPos = hitResult.getBlockPos();

        if (!world.getBlockState(hitPos).isAir()){
            BlockPos fluidPos = hitPos.offset(hitResult.getSide());
            ItemStack handStack = user.getStackInHand(hand);

            if (!world.canPlayerModifyAt(user, fluidPos) || !user.canPlaceOn(fluidPos, hitResult.getSide(), handStack)) {
                return;
            }

            FluidState fluidState = ModFluids.MILK_FLUIDS.get(RegisterUtil.FluidType.STILL.ordinal()).getStill().getDefaultState();
            world.setBlockState(fluidPos, fluidState.with(MilkFluid.LEVEL, 1).with(MilkFluid.FALLING, false).getBlockState(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.playSound(user, fluidPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.emitGameEvent(user, GameEvent.FLUID_PLACE, fluidPos);
        }
    }
}