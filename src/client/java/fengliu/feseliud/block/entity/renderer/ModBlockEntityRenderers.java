package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.block.entity.ModBlockEntitys;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class ModBlockEntityRenderers {

    public static void registerBlockEntityRenderers(){
        BlockEntityRendererRegistry.register(ModBlockEntitys.ICE_CREAM_BAR_MOLD_BLOCK_ENTITY, IceCreamBarMoldBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntitys.MIXER_BLOCK_ENTITY, MixerBlockEntityRenderer::new);
    }
}
