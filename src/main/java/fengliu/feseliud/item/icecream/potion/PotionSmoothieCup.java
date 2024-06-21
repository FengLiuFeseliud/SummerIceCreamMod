package fengliu.feseliud.item.icecream.potion;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.item.icecream.cup.IceCreamCup;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.color.IColorItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.data.client.Models.GENERATED_THREE_LAYERS;

public class PotionSmoothieCup extends IceCreamCup implements IColorItem {
    public static final String SAUCE_PATH = IceCreamCup.PREFIXED_PATH + "sauce_overlay";

    public PotionSmoothieCup(Settings settings, String name, boolean spoon) {
        super(settings, name, spoon);
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIces() {
        return ModItems.POTION_SMOOTHIE_CUPS;
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreamAndSpoons() {
        return ModItems.POTION_SMOOTHIE_CUPS_AND_SPOON;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        IcePotionCup.getStatusEffectInstances(stack, this.getItemLevel()).forEach(statusEffectInstance -> user.addStatusEffect(statusEffectInstance, user));
        ItemStack newStack = super.finishUsing(stack, world, user);
        if (newStack.isOf(this.getItemLevel().getOutItemStack().getItem())){
            return newStack;
        }

        if (!inSpoon()){
            return stack;
        }

        return PotionUtil.setPotion(newStack, PotionUtil.getPotion(stack));
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
        return IceCreamCup.PREFIXED_PATH + this.getItemLevel().getName().replaceAll("potion_", "") + "/";
    }

    @Override
    public String getTextureName() {
        return this.getItemLevel().getSubName().replaceAll("spoon_", "");
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        Identifier cupTexturePath = IdUtil.get(CUP_PATH + (Math.min(this.getItemLevel().getLevel(), 3)));

        if (this.inSpoon()){
            TextureKey LAYER3 = TextureKey.of("layer3");
            new Model(
                    Optional.of(new Identifier("minecraft", "item/generated")), Optional.empty(),
                    TextureKey.LAYER0, TextureKey.LAYER1, TextureKey.LAYER2, LAYER3)
                    .upload(ModelIds.getItemModelId(this),
                            new TextureMap()
                                    .put(TextureKey.LAYER0, IdUtil.get(CUP_SPOON_PATH))
                                    .put(TextureKey.LAYER1, cupTexturePath)
                                    .put(TextureKey.LAYER2, this.getTexturePath())
                                    .put(LAYER3, IdUtil.get(SAUCE_PATH).withSuffixedPath("_" + this.getTextureName())),
                    itemModelGenerator.writer);
            return;
        }

        GENERATED_THREE_LAYERS.upload(ModelIds.getItemModelId(this),
                TextureMap.layered(
                        cupTexturePath,
                        this.getTexturePath(),
                        IdUtil.get(SAUCE_PATH).withSuffixedPath("_" + this.getTextureName())
                ), itemModelGenerator.writer);
    }

    @Override
    public int colorProvider(ItemStack stack, int tintIndex) {
        if (!this.inSpoon()){
            return tintIndex == 2 ? IcePotionCup.getColor(stack) : -1;
        }
        return tintIndex == 3 ? IcePotionCup.getColor(stack) : -1;
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most"),
        THAW_ALL(4, 100, 1, "thaw_all");

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
            return "potion_smoothie_cup";
        }

        @Override
        public BaseItem getItem() {
            return new PotionSmoothieCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), false);
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }

    public enum IceCreamAndSpoonLevels implements IIceCreamLevel {
        SPOON_NOT_THAW(1, 600, 2, "spoon_not_thaw"),
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
                    .hunger(this.gain).saturationModifier((float) (this.gain))
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
            return IceCreamAndSpoonLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "potion_smoothie_cup";
        }

        @Override
        public BaseItem getItem() {
            return new PotionSmoothieCup(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName(), true);
        }

        @Override
        public ItemStack getOutItemStack() {
            return ModItems.ICE_CREAM_CUPS_PACK.getDefaultStack();
        }
    }
}
