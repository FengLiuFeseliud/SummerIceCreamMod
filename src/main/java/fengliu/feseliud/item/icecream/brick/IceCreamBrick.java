package fengliu.feseliud.item.icecream.brick;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCream;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class IceCreamBrick extends IceCreamBar {
    public static final String PREFIXED_PATH = IIceCream.PREFIXED_PATH + "brick" + "/";

    public IceCreamBrick(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreams() {
        return ModItems.ICE_CREAM_BRICKS;
    }

    /**
     * 减一融化
     * @param stack 冰淇淋
     * @return stack 冰淇淋
     */
    @Override
    public ItemStack getIceThawCreamItemStack(ItemStack stack) {
        stack.decrement(1);
        if (stack.isEmpty()){
            return this.getIceCreamLevel().getAllThawItemStack();
        }

        ItemStack newStack = stack.getItem().getDefaultStack();
        newStack.setCount(stack.getCount());
        return newStack;
    }

    @Override
    public String getTextureName() {
        return this.getIceCreamLevel().getName();
    }

    @Override
    public String getPrefixedPath() {
        return PREFIXED_PATH;
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
                    .hunger((int) (1.5f * this.gain)).saturationModifier((float) (this.gain))
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
            return IceCreamBrick.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "ice_cream_brick";
        }

        @Override
        public String getIdName() {
            return this.getName();
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return Items.AIR.getDefaultStack();
        }

        @Override
        public BaseItem getItem() {
            return new IceCreamBrick(new FabricItemSettings().maxCount(16).food(this.getFoodComponent()), this.getName());
        }

        @Override
        public String getTranslations(String translationKey, JsonObject translations) {
            return null;
        }
    }
}

