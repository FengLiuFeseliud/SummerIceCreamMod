package fengliu.feseliud.item.icecream.cup;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ChocolateIceCreamCup extends IceCreamCup{
    public ChocolateIceCreamCup(Settings settings, String name, boolean spoon) {
        super(settings, name, spoon);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreamAndSpoons() {
        return ModItems.CHOCOLATE_ICE_CREAM_CUPS_AND_SPOON;
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIces() {
        return ModItems.CHOCOLATE_ICE_CREAM_CUPS;
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 800, 4, "not_thaw"),
        THAW_HALF(2, 400, 3, "thaw_half"),
        THAW_MOST(3, 200, 2, "thaw_most"),
        THAW_ALMOST_ALL(4, 100, 1, "thaw_almost_all"),
        THAW_ALL(5, 0, 1, "thaw_all");

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
        public String getThawName() {
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
        public String getIdName() {
            return "chocolate_ice_cream_cup";
        }

        @Override
        public BaseItem getItem() {
            return new ChocolateIceCreamCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getIdName(), false);
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }

    public enum IceCreamAndSpoonLevels implements IIceCreamLevel {
        SPOON_NOT_THAW(1, 800, 4, "spoon_not_thaw"),
        SPOON_THAW_HALF(2, 400, 3, "spoon_thaw_half"),
        SPOON_THAW_MOST(3, 200, 2, "spoon_thaw_most"),
        SPOON_THAW_ALMOST_ALL(4, 100, 1, "spoon_thaw_almost_all"),
        SPOON_THAW_ALL(5, 0, 1, "spoon_thaw_all");

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
                    .hunger((int) (1.5 * this.gain)).saturationModifier((float) (this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 80 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5 * this.gain), 0.25f)
                    .alwaysEdible().build();
        }

        @Override
        public int getThawTime() {
            return this.thawTime;
        }

        @Override
        public String getThawName() {
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
        public String getIdName() {
            return "chocolate_ice_cream_cup";
        }

        @Override
        public BaseItem getItem() {
            return new ChocolateIceCreamCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getIdName(), true);
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }

}
