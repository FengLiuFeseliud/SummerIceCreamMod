package fengliu.feseliud.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.*;

public class ListRecipes implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final List<ItemStack> inputs;
    private final ItemStack result;

    static class IceCreamBarMoldRecipesJsonFormat {
        JsonArray inputs;
        String result;
    }

    public ListRecipes(Identifier id, List<ItemStack> inputs, ItemStack result){
        this.id = id;
        this.inputs = inputs;
        this.result = result;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        for (ItemStack input :this.inputs){
            if (inventory.count(input.getItem()) == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return this.getOutput(registryManager).copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public ItemStack getResult(){
        return this.result;
    }

    public List<ItemStack> getInputs(){
        return this.inputs;
    }

    public static class ListRecipesSerializer implements RecipeSerializer<ListRecipes>{
        public static final ListRecipesSerializer INSTANCE = new ListRecipesSerializer();
        public static final Identifier ID = IdUtil.get("list_recipes");

        @Override
        public ListRecipes read(Identifier id, JsonObject json) {
            IceCreamBarMoldRecipesJsonFormat recipeJson = new Gson().fromJson(json, IceCreamBarMoldRecipesJsonFormat.class);

            List<ItemStack> inputs = new ArrayList<>();
            recipeJson.inputs.forEach(input -> {
                inputs.add(Registries.ITEM.getOrEmpty(new Identifier(input.getAsString()))
                        .orElseThrow(() -> new JsonSyntaxException("No such item " + input)).getDefaultStack());
            });

            return new ListRecipes(id, inputs, Registries.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                    .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.result)).getDefaultStack());
        }

        @Override
        public void write(PacketByteBuf buf, ListRecipes recipe) {
            buf.writeItemStack(recipe.getResult());
            for (ItemStack input: recipe.getInputs()){
                buf.writeItemStack(input);
            }
        }

        @Override
        public ListRecipes read(Identifier id, PacketByteBuf buf) {
            ItemStack result = buf.readItemStack();
            List<ItemStack> inputs = new ArrayList<>();
            for (int index = 0; index < 9; index++){
                inputs.add( buf.readItemStack());
            }
            return new ListRecipes(id, inputs, result);
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ListRecipesSerializer.INSTANCE;
    }
    public static class Type implements RecipeType<ListRecipes> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "list_recipes";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}
