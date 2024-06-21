package fengliu.feseliud.item.icecream.cup;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.recipes.builder.ListRecipeJsonBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.Map;
import java.util.function.Consumer;

public class SmoothieCup extends IceCreamCup{
    public SmoothieCup(Settings settings, String name, boolean spoon) {
        super(settings, name, spoon);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIces() {
        return ModItems.SMOOTHIE_CUPS;
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreamAndSpoons() {
        return ModItems.SMOOTHIE_CUPS_AND_SPOON;
    }

    public Map<IceCreamBar, IIceCreamLevel> getPotionSmoothieCup() {
        return ModItems.POTION_SMOOTHIE_CUPS;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty() || !slotStack.isOf(Items.POTION) || ((IIceCreamLevelItem) stack.getItem()).getItemLevel().getLevel() != 1){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Potion potion = PotionUtil.getPotion(slotStack);
        if (potion.getEffects().isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        ItemStack icePotionCupStack = ((Item) this.getPotionSmoothieCup().keySet().toArray()[0]).getDefaultStack();
        PotionUtil.setPotion(icePotionCupStack, potion);

        stack.decrement(1);
        slot.setStack(setItemStack(icePotionCupStack, stack));
        return true;
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        if (this.inSpoon()){
            return;
        }

        int level = this.getItemLevel().getLevel();
        if (level == 4){
            new ListRecipeJsonBuilder(this, ModItems.ICE_CREAM_CUPS_PACK, ModItems.ICE_CUBES.keySet().stream().toList().get(0)).offerTo(exporter);
            return;
        }
        new ListRecipeJsonBuilder(this, this.getIces().keySet().stream().toList().get(level), ModItems.ICE_CUBES.keySet().stream().toList().get(0)).offerTo(exporter);
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most"),
        THAW_ALL(4, 100, 1, "thaw_all");

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
            return null;
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
            return IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "smoothie_cup";
        }

        @Override
        public BaseItem getItem() {
            return new SmoothieCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), false);
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }

    public enum IceCreamAndSpoonLevels implements IIceCreamLevel {
        SPOON_NOT_THAW(1, 600, 2, "spoon_not_thaw"),
        SPOON_THAW_HALF(2, 300, 2, "spoon_thaw_half"),
        SPOON_THAW_MOST(3, 150, 1, "spoon_thaw_most"),
        SPOON_THAW_ALL(4, 100, 1, "spoon_thaw_all");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamAndSpoonLevels(int level, int thawTime, int gain, String thawName){
            this.thawTime = thawTime * 20;
            this.level = level;
            this.gain = gain;
            this.thawName = thawName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                    .hunger(this.gain).saturationModifier((float) (this.gain))
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
            return IceCreamAndSpoonLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "smoothie_cup";
        }

        @Override
        public BaseItem getItem() {
            return new SmoothieCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), true);
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }
}
