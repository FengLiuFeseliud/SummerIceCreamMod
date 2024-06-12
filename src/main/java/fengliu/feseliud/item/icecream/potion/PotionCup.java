package fengliu.feseliud.item.icecream.potion;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.IItemLevel;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PotionCup extends BaseItem implements ILevelItem {
    public PotionCup(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<PotionCup, IItemLevel> getLevelItems() {
        return ModItems.POTION_CUPS;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            return super.finishUsing(stack, world, user);
        }

        IcePotionCup.getStatusEffectInstances(stack, this.getItemLevel()).forEach(statusEffectInstance -> user.addStatusEffect(statusEffectInstance, user));
        super.finishUsing(stack, world, user);
        return PotionUtil.setPotion(this.getNextItemStack(stack), PotionUtil.getPotion(stack));
    }

    @Override
    public Text getName(ItemStack stack) {
        return IcePotionCup.getCupName(stack, this.getItemLevel());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        PotionUtil.buildTooltip(IcePotionCup.getStatusEffectInstances(stack, this.getItemLevel()), tooltip, 1.0f);
    }

    @Override
    public String getPrefixedPath() {
        return IcePotionCup.PREFIXED_PATH;
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
                        ModItems.CUP.getTexturePath()
                ),
                itemModelGenerator.writer);
    }

    public enum PotionCupLevels implements IItemLevel {
        DRINK_ALL(1, 1, "drink_all"),
        DRINK_HALF(2, 1, "drink_half"),
        DRINK_MOST(3, 1, "drink_most");

        private final int level;
        private final int gain;
        private final String subName;

        PotionCupLevels(int level, int gain, String subName){
            this.level = level;
            this.gain = gain;
            this.subName = subName;
        }

        @Override
        public String getSubName() {
            return this.subName;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return PotionCupLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "potion_cup";
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.CUP.getDefaultStack();
        }

        @Override
        public IModItem getItem() {
            return new PotionCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()), this.getName());
        }
    }
}
