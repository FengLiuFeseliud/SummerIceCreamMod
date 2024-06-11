package fengliu.feseliud.item.icecream.bar;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.item.BaseColorItem;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.IIceCreamPack;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PackIceCreamBar extends BaseColorItem implements IIceCreamPack, FabricItem {

    public PackIceCreamBar(DyeColor color, String textureName, int count) {
        super(color, textureName, count);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }
        user.setStackInHand(hand, this.getIceCreamItemStack(user.getStackInHand(hand)));
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        this.updatePackTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        this.appendPackTimeTooltip(stack, world, tooltip, context);
    }

    @Override
    public int packTickOffset() {
        return 2;
    }
}
