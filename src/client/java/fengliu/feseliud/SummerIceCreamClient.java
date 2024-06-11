package fengliu.feseliud;

import fengliu.feseliud.block.ITranslucent;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.renderer.ModBlockEntityRenderers;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.networking.packets.ModS2CPackets;
import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.RegisterUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SummerIceCreamClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		RegisterUtil.baseColorItems.forEach(baseColorItem -> {
			ColorProviderRegistry.ITEM.register((stack, tintIndex) -> baseColorItem.getColor().getMapColor().color, baseColorItem);
		});

		for (Field field: ModFluids.class.getDeclaredFields()) {
			BaseFluid fluid1;
			BaseFluid fluid2;
			try {
				List<BaseFluid> fluids = (List<BaseFluid>) field.get(null);
				fluid1 = fluids.get(RegisterUtil.FluidType.STILL.ordinal());
				fluid2 = fluids.get(RegisterUtil.FluidType.FLOWING.ordinal());
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			if (fluid1.getFluidColor() != -1 && fluid2.getFluidColor() != -1){
				FluidRenderHandlerRegistry.INSTANCE.register(fluid1, fluid2, SimpleFluidRenderHandler.coloredWater(fluid1.getFluidColor()));
			} else {
				FluidRenderHandlerRegistry.INSTANCE.register(fluid1, fluid2, new SimpleFluidRenderHandler(
						IdUtil.getFluidStill(fluid1.getName()).withPrefixedPath("block/"),
						IdUtil.getFluidFlowing(fluid2.getName()).withPrefixedPath("block/")
				));
			}
			BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), fluid1, fluid2);
		}

		for (Field field: ModBlocks.class.getDeclaredFields()) {
			Object block;
			try {
				block = field.get(null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			if (block instanceof ITranslucent){
				BlockRenderLayerMap.INSTANCE.putBlock((Block) block, RenderLayer.getTranslucent());
			}
			if (block instanceof Map<?,?> map){
				map.forEach((level, mblock) -> {
					BlockRenderLayerMap.INSTANCE.putBlock((Block) mblock, RenderLayer.getTranslucent());
				});
			}

		}

		ModBlockEntityRenderers.registerBlockEntityRenderers();
		ModS2CPackets.registerS2CPackets();
	}
}