package fengliu.feseliud;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.recipes.ModRecipes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummerIceCream implements ModInitializer {
	public static final String MOD_ID = "summer_ice_cream";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerAllItems();
		ModBlockItems.registerAllBlockItems();
		ModBlocks.registerAllBlock();
		ModFluids.registerAllFluids();
		ModBlockEntitys.registerAllBlockEntity();
		ModRecipes.registerAllRecipes();
	}
}