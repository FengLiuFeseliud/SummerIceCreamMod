package fengliu.feseliud.item.icecream.brick;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.level.IFoodItemLevel;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ChocolateBrick extends BaseItem implements ILevelItem, FabricItem {
    public ChocolateBrick(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<ChocolateBrick, IFoodItemLevel> getLevelItems() {
        return ModItems.CHOCOLATE_BRICKS;
    }

    @Override
    public ItemStack getNextItemStack(ItemStack stack) {
        stack.decrement(1);
        if (stack.isEmpty()){
            return this.getItemLevel().getOutItemStack();
        }

        ItemStack newStack = stack.copy();
        newStack.setCount(stack.getCount());
        return newStack;
    }

    @Override
    public String getTextureName() {
        return this.getItemLevel().getName();
    }

    public enum ChocolateBrickLevels implements IFoodItemLevel {
        EAT_NOT(1 , 1, "eat_not");

        private final int level;
        private final int gain;
        private final String eatName;

        ChocolateBrickLevels(int level, int gain, String eatName){
            this.level = level;
            this.gain = gain;
            this.eatName = eatName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                    .hunger(2 * this.gain).saturationModifier(1.5F * this.gain)
                    .build();
        }

        @Override
        public String getSubName() {
            return this.eatName;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return ChocolateBrickLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "chocolate_brick";
        }

        @Override
        public String getIdName() {
            return this.getName();
        }

        @Override
        public ChocolateBrick getItem() {
            return new ChocolateBrick(new FabricItemSettings().maxCount(16).food(this.getFoodComponent()), this.getIdName());
        }

        @Override
        public String getTranslations(String translationKey, JsonObject translations) {
            return null;
        }
    }
}
