package fengliu.feseliud.item.icecream.bar;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCream;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.utils.IdUtil;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public class IceCreamBar extends BaseItem implements IIceCream, FabricItem {

    public IceCreamBar(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public boolean inSpoon() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<IceCreamBar, IIceCreamLevel> getIceCreams() {
        return ModItems.ICE_CREAM_BAR;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        this.thawTick(stack, world, entity, slot, selected);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setFireTicks(0);
        attacker.setStackInHand(attacker.getActiveHand(), this.getIceThawCreamItemStack(stack));
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            return super.finishUsing(stack, world, user);
        }

        user.setFireTicks(0);
        ItemStack iceCreamStack = this.getIceThawCreamItemStack(stack);
        super.finishUsing(stack, world, user);
        return iceCreamStack;
    }

    @Override
    public String getTextureName() {
        return this.getIceCreams().get(this).getThawName();
    }

    @Override
    public String getPrefixedPath() {
        return super.getPrefixedPath() + this.getIceCreams().get(this).getIdName() + "/";
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        this.appendThawTimeTooltip(stack, world, tooltip, context);
    }

    public enum IceCreamLevels implements IIceCreamLevel{
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
        public String getIdName() {
            return "ice_cream_bar";
        }

        @Override
        public BaseItem getItem() {
            return new IceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getIdName());
        }
    }
}
