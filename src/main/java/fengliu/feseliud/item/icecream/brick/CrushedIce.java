package fengliu.feseliud.item.icecream.brick;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class CrushedIce extends IceCreamBrick{
    public CrushedIce(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
        return ModItems.CRUSHED_ICE;
    }

    public enum IceCreamLevels implements IIceCreamLevel{
        NOT_THAW(1, 150, 1, "not_thaw");

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
                    .hunger(this.gain)
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 50), 1.0f)
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
            return "crushed_ice";
        }

        @Override
        public String getIdName() {
            return this.getName();
        }

        @Override
        public ItemStack getOutItemStack() {
            return Items.AIR.getDefaultStack();
        }

        @Override
        public BaseItem getItem() {
            return new CrushedIce(new FabricItemSettings().maxCount(16).food(this.getFoodComponent()), this.getName());
        }

        @Override
        public String getTranslations(String translationKey, JsonObject translations) {
            return null;
        }
    }
}
