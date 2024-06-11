package fengliu.feseliud.item.block.icecream;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.CookieIceCreamBlock;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.ModBlockItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class CookieIceCream extends IceCream{
    public CookieIceCream(IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Map<IceCream, IIceCreamBlockLevel> getIceCreams() {
        return ModBlockItems.COOKIE_ICE_CREAMS;
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
                    .hunger(2 * this.gain).saturationModifier((float) (1.5 * this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, this.gain), 1.0f)
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
            return "cookie_ice_cream";
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return Items.AIR.getDefaultStack();
        }

        @Override
        public IceCreamBlock getBlock() {
            return ModBlocks.COOKIE_ICE_CREAM_BLOCKS.get(this);
        }

        @Override
        public BaseBlockItem getItem() {
            return new CookieIceCream(this.getBlock(), new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()));
        }
    }
}
