package fengliu.feseliud.item.icecream.bar;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.recipes.builder.ListRecipeJsonBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Consumer;

public class MilkPopsicle extends IceCreamBar {
    public MilkPopsicle(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
        return ModItems.MILK_POPSICLES;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient() || !(user instanceof PlayerEntity player)){
            return super.finishUsing(stack, world, user);
        }

        player.clearStatusEffects();
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        if (this.getItemLevel().getLevel() != 1){
            return;
        }

        new ListRecipeJsonBuilder(this, ModItems.BAR, Items.MILK_BUCKET).offerTo(exporter);
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamLevels(int level, int thawTime, int gain, String thawName){
            this.thawTime = thawTime * 20;
            this.level = level;
            this.gain = gain;
            this.thawName = thawName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                    .hunger((int) (1.5f * this.gain)).saturationModifier((float) (this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
                    .alwaysEdible().build();
        }

        @Override
        public int getThawTime() {
            return this.thawTime;
        }

        @Override
        public String getSubName() {
            return this.thawName;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return IceCreamBar.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "milk_popsicle";
        }

        @Override
        public BaseItem getItem() {
            return new MilkPopsicle(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName());
        }
    }

}
