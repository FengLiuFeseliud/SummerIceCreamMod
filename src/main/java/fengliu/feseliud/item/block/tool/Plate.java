package fengliu.feseliud.item.block.tool;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.entity.PlateBlockEntity;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.IInventoryItem;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Plate extends BaseBlockItem implements IInventoryItem {
    public Plate(IModBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()){
            return;
        }

        ItemStack tackStack = this.getInventoryLastStack(stack);
        if (!(tackStack.getItem() instanceof IIceCreamLevelItem iceCreamLevelItem)){
            return;
        }

        iceCreamLevelItem.thawTimeToItemStack(tackStack);
        if (iceCreamLevelItem.getThawTimeFromItemStack(tackStack) <= 0){
            this.takeInventoryLastStack(stack);
        }
    }

    public void eatAllFood(ItemStack stack, World world, LivingEntity user){
        this.getStacks(stack).forEach(takeStack -> {
            if (takeStack.isEmpty() || !takeStack.isFood()){
                return;
            }
            takeStack.finishUsing(world, user);
        });
        this.removeInventory(stack);
        stack.setDamage(PlateBlockEntity.SIZE);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user.isSneaking()){
            this.eatAllFood(stack, world, user);
            return stack;
        }

        ItemStack takeStack = this.takeInventoryLastStack(stack);
        if (takeStack.isEmpty() || !takeStack.isFood()){
            return stack;
        }

        takeStack.finishUsing(world, user);
        stack.setDamage(PlateBlockEntity.SIZE - this.size(stack));
        return stack;
    }

    @Override
    public Text getName(ItemStack stack) {
        if (this.isEmpty(stack)){
            return super.getName(stack);
        }
        return Text.translatable(IdUtil.getItemInfo("plate.food"), super.getName(stack), this.getInventoryLastStack(stack).getName());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        ItemStack tackStack = this.getInventoryLastStack(stack);
        if (!(tackStack.getItem() instanceof IIceCreamLevelItem iceCreamLevelItem)){
            super.appendTooltip(stack, world, tooltip, context);
            return;
        }

        super.appendTooltip(stack, world, tooltip, context);
        iceCreamLevelItem.appendThawTimeTooltip(tackStack, world, tooltip, context);
    }
}
