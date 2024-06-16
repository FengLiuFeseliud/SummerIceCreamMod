package fengliu.feseliud.item.icecream.bar;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.recipes.builder.ListRecipeJsonBuilder;
import fengliu.feseliud.utils.IdUtil;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static net.minecraft.data.client.Models.GENERATED;


public class IceCreamBar extends BaseItem implements IIceCreamLevelItem, FabricItem {
    public static final String PREFIXED_PATH = IIceCreamLevelItem.PREFIXED_PATH + "bar" + "/";
    public static final String CHOCOLATE_CRUST_ICE_CREAM_PATH = IIceCreamLevelItem.PREFIXED_PATH + "bar/chocolate_crust_ice_cream_bar_not_thaw";

    public IceCreamBar(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public boolean inSpoon() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
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
        attacker.setStackInHand(attacker.getActiveHand(), this.getNextItemStack(stack));
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.setFireTicks(0);
        ItemStack iceCreamStack = this.getNextItemStack(stack);
        super.finishUsing(stack, world, user);
        return iceCreamStack;
    }

    @Override
    public String getTextureName() {
        return this.getItemLevel().getSubName();
    }

    @Override
    public String getPrefixedPath() {
        return PREFIXED_PATH + this.getItemLevel().getName() + "/";
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

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        IIceCreamLevel iceCreamLevel = this.getItemLevel();
        if (iceCreamLevel.getName().contains("chocolate_crust_") && iceCreamLevel.getLevel() == 1){
            GENERATED.upload(ModelIds.getItemModelId(this), TextureMap.layer0(IdUtil.get(CHOCOLATE_CRUST_ICE_CREAM_PATH)), itemModelGenerator.writer);
            return;
        }
        super.generateModel(itemModelGenerator);
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        IIceCreamLevel iceCreamLevel = this.getItemLevel();
        if (iceCreamLevel.getLevel() != 1){
            return;
        }

        if (iceCreamLevel.getName().contains("chocolate_crust_")){
            new ListRecipeJsonBuilder(this, Registries.ITEM.get(IdUtil.get(this.getItemLevel().getIdName().replaceAll("chocolate_crust_", ""))), ModItems.CHOCOLATE_LIQUID_BUCKET).offerTo(exporter);
        } else {
            Item item = Registries.ITEM.get(IdUtil.get(this.getItemLevel().getName().replaceAll("_bar", "_liquid_bucket")));
            if (item.equals(Items.AIR)){
                item = Registries.ITEM.get(IdUtil.get(this.getItemLevel().getName().replaceAll("_popsicle", "_bucket")));
                if (item.equals(Items.AIR)){
                    item = Registries.ITEM.get(IdUtil.get("milk_" + this.getItemLevel().getName().replaceAll("_bar", "_liquid_bucket")));
                }
            }
            new ListRecipeJsonBuilder(this, ModItems.BAR, item).offerTo(exporter);
        }
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
            return IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "ice_cream_bar";
        }

        @Override
        public BaseItem getItem() {
            return new IceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName());
        }
    }
}
