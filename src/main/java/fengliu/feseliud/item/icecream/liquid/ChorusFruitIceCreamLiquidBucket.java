package fengliu.feseliud.item.icecream.liquid;

import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.item.icecream.bar.ChorusFruitIceCreamBar;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ChorusFruitIceCreamLiquidBucket  extends FoodLiquidBucket {
    public ChorusFruitIceCreamLiquidBucket(String name, List<? extends BaseFluid> fluids, FoodComponent food) {
        super(name, fluids, food);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ChorusFruitIceCreamBar.useChorusFruit(world, user);
        return super.finishUsing(stack, world, user);
    }
}
