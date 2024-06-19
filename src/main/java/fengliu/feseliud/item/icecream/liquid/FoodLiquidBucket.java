package fengliu.feseliud.item.icecream.liquid;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.recipes.builder.ListRecipeJsonBuilder;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * 食物液体桶, 可食用可放置
 */
public class FoodLiquidBucket extends BucketItem implements IModItem {
    public static final String PREFIXED_PATH = IIceCreamLevelItem.PREFIXED_PATH + "bucket" + "/";
    public final String name;

    public FoodLiquidBucket(String name, List<? extends BaseFluid> fluids, FoodComponent food) {
        super(fluids.get(RegisterUtil.FluidType.STILL.ordinal()), new Settings().food(FoodLiquidBucket.setIceCreamLiquidFoodComponent(food)).maxCount(1));
        this.name = name;
    }

    /**
     * 根据提供的食物重设食物桶食物效果
     * @param food 食物效果
     * @return 桶食物效果
     */
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
        BlockState state = world.getBlockState(raycast(world, user, RaycastContext.FluidHandling.WATER).getBlockPos());
        if (state.isOf(ModBlocks.ICE_CREAM_BAR_MOLD_BLOCK) || state.isOf(ModBlocks.ICE_CREAM_MACHINE_BLOCK)){
            return TypedActionResult.pass(user.getStackInHand(hand));
        }

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

    @Override
    public String getPrefixedPath() {
        return PREFIXED_PATH;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.getItemName())));
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        if (this.equals(ModItems.MILK_ICE_CREAM_LIQUID_BUCKET)){
            new ListRecipeJsonBuilder(this, Items.MILK_BUCKET, Items.SUGAR).offerTo(exporter);
        } else if (this.equals(ModItems.CHOCOLATE_LIQUID_BUCKET)){
            new ListRecipeJsonBuilder(this, Items.MILK_BUCKET, ModItems.COOKED_CACAO_BEANS).offerTo(exporter);
        } else if (this.equals(ModItems.CHOCOLATE_ICE_CREAM_LIQUID_BUCKET)){
            new ListRecipeJsonBuilder(this, ModItems.CHOCOLATE_LIQUID_BUCKET, (ItemConvertible) ModItems.ICE_CREAM_BRICKS.keySet().toArray()[0]).offerTo(exporter);
        } else if (this.equals(ModItems.SALT_WATER_BUCKET)){
            new ListRecipeJsonBuilder(this, Items.WATER_BUCKET, ModItems.SALT).offerTo(exporter);
        } else {
            Item item = Registries.ITEM.get(IdUtil.get(this.getItemName().replaceAll("_ice_cream_liquid_bucket", "")));
            if (item.equals(Items.AIR)){
                item = Registries.ITEM.get(new Identifier("minecraft", this.getItemName().replaceAll("_ice_cream_liquid_bucket", "")));
            }

            if (!item.equals(Items.AIR)){
                new ListRecipeJsonBuilder(this, Items.MILK_BUCKET, item).offerTo(exporter);
            }
        }
    }
}
