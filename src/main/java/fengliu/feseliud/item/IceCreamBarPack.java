package fengliu.feseliud.item;

import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.item.icecream.IIceCreamPack;
import fengliu.feseliud.item.icecream.bar.PackIceCreamBar;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;

import java.util.Optional;
import java.util.function.Consumer;


public class IceCreamBarPack extends BaseDyeColorItem {
    public IceCreamBarPack(DyeColor color, String name, int count) {
        super(color, name, count);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (!(slotStack.getItem() instanceof IIceCreamLevelItem iceCream)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (iceCream.getLevelItems().keySet().stream().toList().indexOf(iceCream) != 0){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Optional<PackIceCreamBar> packItemOptional= ModItems.PACK_ICE_CREAM.stream().filter(packIceCreamBar -> packIceCreamBar.getColor() == this.getColor()).findFirst();
        if (packItemOptional.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        ItemStack packIceCreamStack = (packItemOptional.get()).getDefaultStack();
        packIceCreamStack.getOrCreateNbt().put(IIceCreamPack.PACK_ICE_CREAM_KEY, slotStack.writeNbt(new NbtCompound()));

        stack.decrement(1);
        slot.setStack(packIceCreamStack);
        return true;
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        if (this.getDyeColor().equals(DyeColor.WHITE)){
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, this, 1)
                    .input(Items.PAPER)
                    .input(Items.PAPER)
                    .criterion(FabricRecipeProvider.hasItem(this),
                            FabricRecipeProvider.conditionsFromItem(this))
                    .offerTo(exporter);
            return;
        }

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, this, 1)
                .input(Items.PAPER)
                .input(Items.PAPER)
                .input(DyeItem.byColor(this.getDyeColor()))
                .criterion(FabricRecipeProvider.hasItem(this),
                        FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }
}
