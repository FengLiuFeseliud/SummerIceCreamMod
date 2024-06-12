package fengliu.feseliud.item.icecream.cup;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.CookieIceCreamBar;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class CookieIceCreamCup extends IceCreamCup{
    public CookieIceCreamCup(Settings settings, String name, boolean spoon) {
        super(settings, name, spoon);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreamAndSpoons() {
        return ModItems.COOKIE_ICE_CREAM_CUPS_AND_SPOON;
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIces() {
        return ModItems.COOKIE_ICE_CREAM_CUPS;
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most"),
        THAW_ALL(4, 150, 1, "thaw_all");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamLevels(int level, int thawTime, int gain, String thawName) {
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
            return CookieIceCreamBar.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "cookie_ice_cream_cup";
        }

        @Override
        public BaseItem getItem() {
            return new CookieIceCreamCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), false);
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }

    public enum IceCreamAndSpoonLevels implements IIceCreamLevel {
        SPOON_NOT_THAW(1, 600, 3, "spoon_not_thaw"),
        SPOON_THAW_HALF(2, 300, 2, "spoon_thaw_half"),
        SPOON_THAW_MOST(3, 150, 1, "spoon_thaw_most"),
        SPOON_THAW_ALL(4, 0, 1, "spoon_thaw_all");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamAndSpoonLevels(int level, int thawTime, int gain, String thawName) {
            this.thawTime = thawTime * 20;
            this.level = level;
            this.gain = gain;
            this.thawName = thawName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                    .hunger((int) (2.5f * this.gain)).saturationModifier((float) (1.5 * this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, this.gain), 1.0f)
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
            return CookieIceCreamBar.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "cookie_ice_cream_cup";
        }

        @Override
        public BaseItem getItem() {
            return new CookieIceCreamCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), true);
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }
}
