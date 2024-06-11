package fengliu.feseliud.item.icecream.bar;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

import java.util.Map;

public class ChocolateCrustGoldenAppleIceCreamBar extends IceCreamBar {
    public ChocolateCrustGoldenAppleIceCreamBar(Item.Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreams() {
        return ModItems.CHOCOLATE_CRUST_GOLDEN_APPLE_ICE_CREAM_BARS;
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 4, "not_thaw"),
        THAW_HALF(2, 300, 3, "thaw_half"),
        THAW_MOST(3, 150, 2, "thaw_most"),
        THAW_ALMOST_ALL(4, 100, 1, "thaw_almost_all");

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
            return new FoodComponent.Builder()
                    .hunger(2 * this.gain).saturationModifier(this.gain)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 80 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400), 1.0f)
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
        public String getName() {
            return "chocolate_crust_golden_apple_ice_cream_bar";
        }

        @Override
        public BaseItem getItem() {
            return new ChocolateCrustGoldenAppleIceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName());
        }
    }
}