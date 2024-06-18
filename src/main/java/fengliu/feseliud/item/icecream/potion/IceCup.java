package fengliu.feseliud.item.icecream.potion;

import com.google.gson.JsonObject;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.item.icecream.brick.IceCube;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Map;

public class IceCup extends IceCreamBar {
    public IceCup(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
        return ModItems.ICE_CUPS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if (player == null || !world.getBlockState(raycast(world, player, RaycastContext.FluidHandling.ANY).getBlockPos()).isOf(Blocks.WATER)) {
            return super.useOnBlock(context);
        }

        player.setStackInHand(context.getHand(), this.setItemStack(ModItems.ICE_POTION_CUPS.keySet().stream().toList().get(0).getDefaultStack(), context.getStack()));
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty() || !slotStack.isOf(Items.POTION)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Potion potion = PotionUtil.getPotion(slotStack);
        if (potion.getEffects().isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        ItemStack icePotionCupStack = ((IcePotionCup) ModItems.ICE_POTION_CUPS.keySet().toArray()[0]).getDefaultStack();
        PotionUtil.setPotion(icePotionCupStack, potion);

        stack.decrement(1);
        slot.setStack(setItemStack(icePotionCupStack, stack));
        return true;
    }

    @Override
    public String getTextureName() {
        return this.getItemLevel().getName();
    }

    @Override
    public String getPrefixedPath() {
        return IModItem.PREFIXED_PATH;
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
            return IceCube.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "ice_cup";
        }

        @Override
        public String getIdName() {
            return this.getName();
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.POTION_CUPS.keySet().stream().toList().get(2).getDefaultStack();
        }

        @Override
        public BaseItem getItem() {
            return new IceCup(new FabricItemSettings().maxCount(1).food(this.getFoodComponent()), this.getName());
        }

        @Override
        public String getTranslations(String translationKey, JsonObject translations) {
            return null;
        }
    }
}
