package fengliu.feseliud.item.icecream.cup;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCream;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Map;

public class IceCreamCup extends IceCreamBar {
    public static final String PREFIXED_PATH = IIceCream.PREFIXED_PATH + "cup" + "/";

    private final boolean spoon;

    public IceCreamCup(Settings settings, String name, boolean spoon) {
        super(settings, name);
        this.spoon = spoon;
    }

    public boolean isSpoon() {
        return this.spoon;
    }

    public Map<IceCreamBar, IIceCreamLevel> getIceCreamAndSpoons(){
        return ModItems.ICE_CREAM_CUPS_AND_SPOON;
    }

    public Map<IceCreamBar, IIceCreamLevel> getIces(){
        return ModItems.ICE_CREAM_CUPS;
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreams() {
        if (this.isSpoon()){
            return this.getIceCreamAndSpoons();
        }
        return this.getIces();
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (this.isSpoon()){
            ItemStack nextIceCream = super.finishUsing(stack, world, user);
            if (nextIceCream.isOf(this.getIceCreamLevel().getAllThawItemStack().getItem())){
                user.dropStack(ModItems.SPOON.getDefaultStack());
            }
            return nextIceCream;
        }
        return stack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (this.isSpoon()){
            return UseAction.DRINK;
        }
        return UseAction.NONE;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        IIceCreamLevel iceCreamLevel = this.getIceCreamLevel();
        if (iceCreamLevel.getLevel() == iceCreamLevel.getMaxLevel()){
            return;
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public String getPrefixedPath() {
        return PREFIXED_PATH + this.getIceCreamLevel().getName() + "/";
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most"),
        THAW_ALL(4, 0, 1, "thaw_all");

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
            return null;
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
            return "ice_cream_cup";
        }

        @Override
        public BaseItem getItem() {
            return new IceCreamCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), false);
        }

        @Override
        public ItemStack getAllThawItemStack() {
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

        IceCreamAndSpoonLevels(int level, int thawTime, int gain, String thawName){
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
            return IceCreamAndSpoonLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "ice_cream_cup";
        }

        @Override
        public BaseItem getItem() {
            return new IceCreamCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), true);
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }
}
