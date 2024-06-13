package fengliu.feseliud.item.icecream.brick;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.IceCup;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.item.icecream.potion.IcePotionCup;
import fengliu.feseliud.item.icecream.potion.PotionCup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import java.util.Map;
import java.util.Optional;

public class IceCube extends IceCreamBrick{
    public IceCube(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
        return ModItems.ICE_CUBES;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty() || !(slotStack.isOf(ModItems.CUP) || slotStack.getItem() instanceof PotionCup) || slotStack.getCount() > 1){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        stack.decrement(1);
        if (!(slotStack.getItem() instanceof PotionCup potionCup)){
            ItemStack iceCupStack = ((IceCup) ModItems.ICE_CUPS.keySet().toArray()[0]).getDefaultStack();
            slot.setStack(setItemStack(iceCupStack, stack));
            return true;
        }

        Optional<IcePotionCup> icePotionCupOptional = ModItems.ICE_POTION_CUPS.keySet().stream().filter(icePotionCup -> icePotionCup.getItemLevel().getLevel() == potionCup.getItemLevel().getLevel()).findAny();
        if (icePotionCupOptional.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }
        slot.setStack(setItemStack(PotionUtil.setPotion(icePotionCupOptional.get().getDefaultStack(), PotionUtil.getPotion(slotStack)), stack));
        return true;
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
            return "ice_cube";
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
            return new IceCube(new FabricItemSettings().maxCount(16).food(this.getFoodComponent()), this.getName());
        }

        @Override
        public String getTranslations(String translationKey, JsonObject translations) {
            return null;
        }
    }
}
