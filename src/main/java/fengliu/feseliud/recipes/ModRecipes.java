package fengliu.feseliud.recipes;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {

    public static void registerAllRecipes(){
        Registry.register(Registries.RECIPE_SERIALIZER, IceCreamBarMoldRecipes.IceCreamBarMoldRecipesSerializer.ID, IceCreamBarMoldRecipes.IceCreamBarMoldRecipesSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, IdUtil.get(IceCreamBarMoldRecipes.Type.ID), IceCreamBarMoldRecipes.Type.INSTANCE);
    }
}
