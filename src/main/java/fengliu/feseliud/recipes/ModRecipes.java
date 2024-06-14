package fengliu.feseliud.recipes;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {

    public static void registerAllRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, ListRecipes.ListRecipesSerializer.ID, ListRecipes.ListRecipesSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, IdUtil.get(ListRecipes.Type.ID), ListRecipes.Type.INSTANCE);
    }
}
