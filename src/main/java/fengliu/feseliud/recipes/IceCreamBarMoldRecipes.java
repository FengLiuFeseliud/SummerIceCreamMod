package fengliu.feseliud.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.*;

public class IceCreamBarMoldRecipes implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final List<ItemStack> inputs;
    private final ItemStack result;

    static class IceCreamBarMoldRecipesJsonFormat {
        JsonArray inputs;
        String result;
    }

    public IceCreamBarMoldRecipes(Identifier id, List<ItemStack> inputs, ItemStack result){
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

    public static class IceCreamBarMoldRecipesSerializer implements RecipeSerializer<IceCreamBarMoldRecipes>{
        public static final IceCreamBarMoldRecipesSerializer INSTANCE = new IceCreamBarMoldRecipesSerializer();
        public static final Identifier ID = IdUtil.get("ice_cream_bar_mold_recipes");

        @Override
        public IceCreamBarMoldRecipes read(Identifier id, JsonObject json) {
            IceCreamBarMoldRecipesJsonFormat recipeJson = new Gson().fromJson(json, IceCreamBarMoldRecipesJsonFormat.class);

            List<ItemStack> inputs = new ArrayList<>();
            recipeJson.inputs.forEach(input -> {
                inputs.add(Registries.ITEM.getOrEmpty(new Identifier(input.getAsString()))
                        .orElseThrow(() -> new JsonSyntaxException("No such item " + input)).getDefaultStack());
            });

            return new IceCreamBarMoldRecipes(id, inputs, Registries.ITEM.getOrEmpty(new Identifier(recipeJson.result))
                    .orElseThrow(() -> new JsonSyntaxException("No such item " + recipeJson.result)).getDefaultStack());
        }

        @Override
        public void write(PacketByteBuf buf, IceCreamBarMoldRecipes recipe) {
            buf.writeItemStack(recipe.getResult());
            for (ItemStack input: recipe.getInputs()){
                buf.writeItemStack(input);
            }
        }

        @Override
        public IceCreamBarMoldRecipes read(Identifier id, PacketByteBuf buf) {
            ItemStack result = buf.readItemStack();
            List<ItemStack> inputs = new ArrayList<>();
            for (int index = 0; index < 3; index++){
                inputs.add( buf.readItemStack());
            }
            return new IceCreamBarMoldRecipes(id, inputs, result);
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IceCreamBarMoldRecipesSerializer.INSTANCE;
    }
    public static class Type implements RecipeType<IceCreamBarMoldRecipes> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "ice_cream_bar_mold_recipes";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}
