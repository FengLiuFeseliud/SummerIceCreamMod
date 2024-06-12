package fengliu.feseliud.item.block.icecream;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.utils.IdUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class IceCream extends BaseBlockItem implements IIceCreamLevelItem {
    public IceCream(IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean inSpoon() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<IceCream, IIceCreamBlockLevel> getLevelItems() {
        return ModBlockItems.ICE_CREAMS;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        this.thawTick(stack, world, entity, slot, selected);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setFireTicks(0);
        attacker.setStackInHand(attacker.getActiveHand(), this.getNextItemStack(stack));
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            return super.finishUsing(stack, world, user);
        }

        user.setFireTicks(0);
        ItemStack iceCreamStack = this.getNextItemStack(stack);
        super.finishUsing(stack, world, user);
        return iceCreamStack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.getItemLevel().getName())));
        this.appendThawTimeTooltip(stack, world, tooltip, context);
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
                    .hunger((int) (1.5f * this.gain)).saturationModifier((float) (this.gain))
                    .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
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
            return IceCream.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "ice_cream_block";
        }

        @Override
        public ItemStack getOutItemStack() {
            return Items.AIR.getDefaultStack();
        }

        @Override
        public IceCreamBlock getBlock() {
            return ModBlocks.ICE_CREAM_BLOCKS.get(this);
        }

        @Override
        public BaseBlockItem getItem() {
            return new IceCream(this.getBlock(), new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()));
        }
    }
}
