package fengliu.feseliud;

import fengliu.feseliud.utils.RegisterUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class SummerIceCreamClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		RegisterUtil.baseColorItems.forEach(baseColorItem -> {
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> baseColorItem.getColor().getMapColor().color, baseColorItem);
		});
	}
}