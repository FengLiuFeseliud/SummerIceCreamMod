package fengliu.feseliud;

import fengliu.feseliud.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummerIceCream implements ModInitializer {
	public static final String MOD_ID = "summer_ice_cream";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerAllItems();
	}
}