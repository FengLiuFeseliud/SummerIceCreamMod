package fengliu.feseliud.item.icecream.liquid;

import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class IceCreamLiquidBucket extends BucketItem implements IModItem {
    public final String name;

    public IceCreamLiquidBucket(String name, Fluid fluid, FoodComponent food) {
        super(fluid, new Settings().food(IceCreamLiquidBucket.setIceCreamLiquidFoodComponent(food)).maxCount(1));
        this.name = name;
    }

    public IceCreamLiquidBucket(String name, List<? extends BaseFluid> fluids, FoodComponent food) {
        super(fluids.get(RegisterUtil.FluidType.STILL.ordinal()), new Settings().food(IceCreamLiquidBucket.setIceCreamLiquidFoodComponent(food)).maxCount(1));
        this.name = name;
    }

    public static FoodComponent setIceCreamLiquidFoodComponent(FoodComponent food){
        FoodComponent.Builder iceCreamLiquid = new FoodComponent.Builder().hunger(food.getHunger() + 1).saturationModifier(food.getSaturationModifier() + 1);
        food.getStatusEffects().forEach(statusEffectInstanceFloatPair -> {
            iceCreamLiquid.statusEffect(statusEffectInstanceFloatPair.getFirst(), statusEffectInstanceFloatPair.getSecond());
        });
        return iceCreamLiquid.alwaysEdible().build();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        TypedActionResult<ItemStack> result = super.use(world, user, hand);
        if (result.getResult() == ActionResult.FAIL || result.getResult() == ActionResult.PASS){
            user.setCurrentHand(hand);
            return TypedActionResult.consume(result.getValue());
        }
        return result;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()){
            return super.finishUsing(stack, world, user);
        }

        int slot = ((PlayerEntity) user).getInventory().getSlotWithStack(stack);
        ((PlayerEntity) user).getInventory().setStack(slot, Items.BUCKET.getDefaultStack());
        return super.finishUsing(stack, world, user);
    }

    @Override
    public String getItemName() {
        return this.name;
    }

    @Override
    public String getTextureName() {
        return this.name;
    }
}