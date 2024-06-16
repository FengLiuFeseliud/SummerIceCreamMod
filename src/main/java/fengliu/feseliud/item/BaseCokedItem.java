package fengliu.feseliud.item;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class BaseCokedItem extends BaseItem{
    public final Item cookItem;
    public final int cookExperience;
    public final int cookTime;

    public BaseCokedItem(Settings settings, Item cookItem, int cookExperience, int cookTime, String name) {
        super(settings, name);
        this.cookItem = cookItem;
        this.cookTime = cookTime;
        this.cookExperience = cookExperience;
    }

    public BaseCokedItem(Settings settings, Item cookItem, String name) {
        this(settings, cookItem, 1, 200, name);
    }

    public BaseCokedItem(String name, Item cookItem, int count) {
        this(new Settings().maxCount(count), cookItem, 1, 200, name);
    }

    @Override
    public void generateRecipe(Consumer<RecipeJsonProvider> exporter) {
        CookingRecipeJsonBuilder
                .createSmelting(Ingredient.ofItems(this.cookItem), RecipeCategory.MISC, this, this.cookExperience, this.cookTime)
                .criterion(FabricRecipeProvider.hasItem(this), FabricRecipeProvider.conditionsFromItem(this))
                .offerTo(exporter);
    }
}
