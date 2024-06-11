package fengliu.feseliud.item.block.icecream;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.bar.ChorusFruitIceCreamBar;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.Map;

public class ChorusFruitIceCream extends IceCream {
    public ChorusFruitIceCream(IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ChorusFruitIceCreamBar.useChorusFruit(world, user);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public Map<IceCream, IIceCreamBlockLevel> getIceCreams() {
        return ModBlockItems.CHORUS_FRUIT_ICE_CREAMS;
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
            return "chorus_fruit_ice_cream";
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return Items.AIR.getDefaultStack();
        }

        @Override
        public IceCreamBlock getBlock() {
            return ModBlocks.CHORUS_FRUIT_ICE_CREAM_BLOCKS.get(this);
        }

        @Override
        public BaseBlockItem getItem() {
            return new ChorusFruitIceCream(this.getBlock(), new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()));
        }
    }
}
