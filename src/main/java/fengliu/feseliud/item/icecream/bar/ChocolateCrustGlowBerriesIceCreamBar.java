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

public class ChocolateCrustGlowBerriesIceCreamBar extends IceCreamBar {
    public ChocolateCrustGlowBerriesIceCreamBar(Item.Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
        return ModItems.CHOCOLATE_CRUST_GLOW_BERRIES_ICE_CREAM_BARS;
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
                    .hunger(2 * this.gain).saturationModifier(1.5f * this.gain)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 80 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5 * this.gain), 0.25f)
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
            return IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "chocolate_crust_glow_berries_ice_cream_bar";
        }

        @Override
        public BaseItem getItem() {
            return new ChocolateCrustGlowBerriesIceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName());
        }
    }
}