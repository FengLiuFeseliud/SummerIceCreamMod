package fengliu.feseliud.item.block.icecream;

import com.google.gson.JsonObject;
import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.GlowBerriesIceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

import java.util.Map;

public class GlowBerriesIceCream extends IceCream{
    public GlowBerriesIceCream(IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Map<IceCream, IIceCreamBlockLevel> getIceCreams() {
        return ModBlockItems.GLOW_BERRIES_ICE_CREAMS;
    }

    public enum IceCreamLevels implements IIceCreamBlockLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most");

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
                    .hunger(2 * this.gain).saturationModifier((float) (this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100 * this.gain), 1.0f)
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
            return "glow_berries_ice_cream";
        }

        @Override
        public IceCreamBlock getBlock() {
            return ModBlocks.GLOW_BERRIES_ICE_CREAM_BLOCKS.get(this);
        }

        @Override
        public BaseBlockItem getItem() {
            return new GlowBerriesIceCream(this.getBlock(), new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()));
        }
    }
}
