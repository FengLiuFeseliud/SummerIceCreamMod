package fengliu.feseliud.item.icecream.potion;

import com.mojang.datafixers.kinds.IdF;
import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.IceCup;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.mixin.MixinStatusEffectInstance;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.IItemLevel;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IcePotionCup extends BaseItem implements IIceCreamLevelItem, FabricItem {
    public static final String PREFIXED_PATH = IIceCreamLevelItem.PREFIXED_PATH + "potion" + "/";

    public IcePotionCup(Settings settings, String name) {
        super(settings, name);
    }

    public static int getColor(ItemStack stack){
        Potion potion = PotionUtil.getPotion(stack);
        if (potion.equals(Potions.EMPTY)){
            return PotionUtil.getColor(Potions.WATER);
        }
        return PotionUtil.getColor(potion);
    }

    public static List<StatusEffectInstance> getStatusEffectInstances(ItemStack stack, IItemLevel level){
        List<StatusEffectInstance> statusEffectInstances = new ArrayList<>();
        PotionUtil.getPotion(stack).getEffects().forEach(statusEffectInstance -> {
            StatusEffectInstance effectInstance = new StatusEffectInstance(statusEffectInstance);
            ((MixinStatusEffectInstance) effectInstance).setDuration(effectInstance.getDuration() / level.getMaxLevel() * level.getGain());
            statusEffectInstances.add(effectInstance);
        });
        if (stack.getItem() instanceof IcePotionCup){
            statusEffectInstances.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * level.getGain()));
        }
        return statusEffectInstances;
    }

    public static Text getCupName(ItemStack stack, IItemLevel level){
        if (PotionUtil.getPotion(stack) == Potions.EMPTY){
            return Text.translatable(IdUtil.getItemName(level.getIdName()), Text.translatable(Blocks.WATER.getTranslationKey()));
        }
        return Text.translatable(IdUtil.getItemName(level.getIdName()), Text.translatable(PotionUtil.getPotion(stack).finishTranslationKey("item.minecraft.potion.effect.")));
    }

    @Override
    public boolean inSpoon() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<IcePotionCup, IIceCreamLevel> getLevelItems() {
        return ModItems.ICE_POTION_CUPS;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        this.thawTick(stack, world, entity, slot, selected);
    }

    @Override
    public ItemStack thaw(ItemStack stack, PlayerEntity player) {
        Optional<PotionCup> optional = ModItems.POTION_CUPS.keySet().stream().filter(item -> item.getItemLevel().getLevel() == this.getItemLevel().getLevel() - 1).findAny();
        if (optional.isEmpty()){
            return PotionUtil.setPotion(((Item) ModItems.POTION_CUPS.keySet().toArray()[0]).getDefaultStack(), PotionUtil.getPotion(stack));
        }

        ItemStack potionCup = optional.get().getDefaultStack();
        potionCup.setDamage(stack.getDamage() - 1);
        return PotionUtil.setPotion(potionCup, PotionUtil.getPotion(stack));
    }

    public static ItemStack resetItemStack(ILevelItem cup, ItemStack stack) {
        ItemStack nextItem = cup.getNextItemStack(stack);
        if (nextItem.isOf(ModItems.CUP)){
            return nextItem;
        }
        return PotionUtil.setPotion(nextItem, PotionUtil.getPotion(stack));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.setFireTicks(0);
        IcePotionCup.getStatusEffectInstances(stack, this.getItemLevel()).forEach(statusEffectInstance -> user.addStatusEffect(statusEffectInstance, user));
        return IcePotionCup.resetItemStack(this, stack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public Text getName(ItemStack stack) {
        return IcePotionCup.getCupName(stack, this.getItemLevel());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        this.appendThawTimeTooltip(stack, world, tooltip, context);
        PotionUtil.buildTooltip(IcePotionCup.getStatusEffectInstances(stack, this.getItemLevel()), tooltip, 1.0f);
    }

    @Override
    public String getPrefixedPath() {
        return PREFIXED_PATH;
    }

    @Override
    public String getTextureName() {
        return "ice_potion_cup_overlay_" + this.getItemLevel().getSubName();
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        Models.GENERATED_TWO_LAYERS.upload(ModelIds.getItemModelId(this),
                TextureMap.layered(
                        IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath()),
                        ((IceCup) ModItems.ICE_CUPS.keySet().toArray()[0]).getTexturePath()
                ),
                itemModelGenerator.writer);
    }

    public enum IceCreamLevels implements IIceCreamLevel{
        DRINK_ALL(1, 150, 2, "drink_all"),
        DRINK_HALF(2, 300, 2, "drink_half"),
        DRINK_MOST(3, 450, 1, "drink_most");

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
            return "ice_potion_cup";
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.CUP.getDefaultStack();
        }

        @Override
        public BaseItem getItem() {
            return new IcePotionCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()), this.getName());
        }
    }
}