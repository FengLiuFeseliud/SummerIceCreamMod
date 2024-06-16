package fengliu.feseliud.item.block;

import com.google.gson.JsonObject;
import fengliu.feseliud.block.ChocolateBlock;
import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.item.block.icecream.IceCream;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class Chocolate extends BaseBlockItem implements ILevelItem {
    public Chocolate( IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Chocolate, ChocolateLevels> getLevelItems() {
        return ModBlockItems.CHOCOLATES;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            return super.finishUsing(stack, world, user);
        }

        ItemStack iceCreamStack = this.getNextItemStack(stack);
        super.finishUsing(stack, world, user);
        return iceCreamStack;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(IdUtil.getItemTooltip(this.getItemLevel().getName())));
    }

    public enum ChocolateLevels implements IBlockItemLevel, IFoodItemLevel {
        EAT_NOT(1 , 3, "eat_not"),
        EAT_HALF(2, 2, "eat_half"),
        EAT_MOST(3, 1, "eat_most");

        private final int level;
        private final int gain;
        private final String eatName;

        ChocolateLevels(int level, int gain, String eatName){
            this.level = level;
            this.gain = gain;
            this.eatName = eatName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                    .hunger(2 * this.gain).saturationModifier(1.5F * this.gain)
                    .build();
        }

        @Override
        public String getSubName() {
            return this.eatName;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return ChocolateLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getName() {
            return "chocolate_block";
        }

        @Override
        public ChocolateBlock getBlock() {
            return ModBlocks.CHOCOLATE_BLOCKS.get(this);
        }

        @Override
        public BaseBlockItem getItem() {
            return new Chocolate(this.getBlock(), new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()));
        }
    }
}
