package fengliu.feseliud.item.icecream.bar;

import fengliu.feseliud.item.BaseItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.Map;

public class ChorusFruitIceCreamBar extends IceCreamBar {
    public ChorusFruitIceCreamBar(Item.Settings settings, String name) {
        super(settings, name);
    }

    /**
     * 设置紫颂果物品 CD
     * @param world 世界
     * @param user 紫颂果用户
     */
    public static void useChorusFruit(World world, LivingEntity user){
        Items.CHORUS_FRUIT.finishUsing(Items.CHORUS_FRUIT.getDefaultStack(), world, user);
        if (!(user instanceof PlayerEntity player)) {
            return;
        }

        ModItems.CHORUS_FRUIT_ICE_CREAM_BARS.forEach(((item, iLevelItem) -> player.getItemCooldownManager().set(item, 20)));
        ModItems.CHORUS_FRUIT_ICE_CREAM_CPUS.forEach(((item, iLevelItem) -> player.getItemCooldownManager().set(item, 20)));
        ModItems.CHOCOLATE_CRUST_CHORUS_FRUIT_ICE_CREAM_BARS.forEach(((item, iLevelItem) -> player.getItemCooldownManager().set(item, 20)));
        ModItems.CHOCOLATE_CHORUS_FRUIT_ICE_CREAM_CPUS.forEach(((item, iLevelItem) -> player.getItemCooldownManager().set(item, 20)));
        ModItems.CHORUS_FRUIT_ICE_CREAM_BRICKS.forEach(((item, iLevelItem) -> player.getItemCooldownManager().set(item, 20)));
        ModBlockItems.CHORUS_FRUIT_ICE_CREAMS.forEach(((item, iLevelItem) -> player.getItemCooldownManager().set(item, 20)));
        player.getItemCooldownManager().set(ModItems.CHORUS_FRUIT_ICE_CREAM_LIQUID_BUCKET, 20);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ChorusFruitIceCreamBar.useChorusFruit(world, user);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getLevelItems() {
        return ModItems.CHORUS_FRUIT_ICE_CREAM_BARS;
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamLevels(int level, int thawTime, int gain, String thawName) {
            this.thawTime = thawTime * 20;
            this.level = level;
            this.gain = gain;
            this.thawName = thawName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                    .hunger((int) (2.5f * this.gain)).saturationModifier((float) (this.gain))
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
            return "chorus_fruit_ice_cream_bar";
        }

        @Override
        public BaseItem getItem() {
            return new ChorusFruitIceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getName());
        }
    }
}
