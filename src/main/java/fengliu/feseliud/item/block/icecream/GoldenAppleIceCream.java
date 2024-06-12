package fengliu.feseliud.item.block.icecream;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class GoldenAppleIceCream extends IceCream{
    public GoldenAppleIceCream(IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Map<IceCream, IIceCreamBlockLevel> getLevelItems() {
        return ModBlockItems.GOLDEN_APPLE_ICE_CREAMS;
    }

    public enum IceCreamLevels implements IIceCreamBlockLevel{
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
                    .hunger(2 * this.gain).saturationModifier((float) (this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400), 1.0f)
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
            return "golden_apple_ice_cream";
        }

        @Override
        public ItemStack getOutItemStack() {
            return Items.AIR.getDefaultStack();
        }

        @Override
        public IceCreamBlock getBlock() {
            return ModBlocks.GOLDEN_APPLE_ICE_CREAM_BLOCKS.get(this);
        }

        @Override
        public BaseBlockItem getItem() {
            return new GoldenAppleIceCream(this.getBlock(), new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()));
        }
    }
}
