package fengliu.feseliud.recipes.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fengliu.feseliud.recipes.ListRecipes;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ListRecipeJsonBuilder extends RecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final Item output;
    private final ItemConvertible[] inputs;

    public ListRecipeJsonBuilder(ItemConvertible output, ItemConvertible... input){
        this.output = output.asItem();
        this.inputs = input;
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, CriterionConditions conditions) {
        return null;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        return null;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        exporter.accept(new ListRecipeJsonProvider(recipeId, this.output, this.inputs));
    }

    public static class ListRecipeJsonProvider extends RecipeJsonBuilder.CraftingRecipeJsonProvider {
        private final ItemConvertible output;
        private final ItemConvertible[] inputs;
        private final Identifier recipeId;

        protected ListRecipeJsonProvider(Identifier recipeId, ItemConvertible output, ItemConvertible[] inputs) {
            super(null);
            this.recipeId = recipeId;
            this.output = output;
            this.inputs = inputs;
        }

        @Override
        public Identifier getRecipeId() {
            return this.recipeId;
        }

        @Override
        public void serialize(JsonObject json) {
            JsonArray inputs = new JsonArray();
            for(ItemConvertible itemConvertible: this.inputs){
                inputs.add(Registries.ITEM.getId(itemConvertible.asItem()).toString());
            }

            json.add("inputs", inputs);
            json.addProperty("result", Registries.ITEM.getId(this.output.asItem()).toString());
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return ListRecipes.ListRecipesSerializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public Identifier getAdvancementId() {
            return null;
        }
    }
}
