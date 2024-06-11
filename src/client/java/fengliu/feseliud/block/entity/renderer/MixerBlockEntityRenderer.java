package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.SummerIceCreamClient;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.entity.MixerBlockEntity;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.utils.IdUtil;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

import static net.minecraft.client.render.RenderPhase.TRANSLUCENT_NO_CRUMBLING_PROGRAM;

public class MixerBlockEntityRenderer implements BlockEntityRenderer<MixerBlockEntity> {
    private static final float yLightFactor = 0.5f;
    private static final float zLightFactor = 0.8f;
    private static final float xLightFactor = 0.6f;

    private final MinecraftClient client = MinecraftClient.getInstance();
    public final BlockEntityRendererFactory.Context ctx;
    private VertexConsumer vertexConsumer;
    private MatrixStack.Entry worldMatrix;
    private int light;

    public MixerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){
        this.ctx = ctx;
    }

    // 感谢 github @chimericdream 的 minekea-fabric 的代码让我解决了液体绘制这个难题！！！
    // https://github.com/chimericdream/minekea-fabric/blob/main/src/main/java/com/chimericdream/minekea/client/render/block/GlassJarBlockEntityRenderer.java
    @Override
    public void render(MixerBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = be.getWorld();
        BlockPos pos = be.getPos();
        Fluid fluid = ModFluids.MILK_FLUIDS.get(0);

        Sprite[] sprites =  FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(world, pos, fluid.getDefaultState());
        int color = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(world, pos, fluid.getDefaultState());
        Sprite topSprite = sprites[0];
        Sprite sideSprite = sprites[1];

        float colorR = (float) (color >> 16 & 255) / 255.0F;
        float colorG = (float) (color >> 8 & 255) / 255.0F;
        float colorB = (float) (color & 255) / 255.0F;
        float xColorR = colorR * xLightFactor;
        float xColorG = colorG * xLightFactor;
        float xColorB = colorB * xLightFactor;
        float yColorR = colorR * yLightFactor;
        float yColorG = colorG * yLightFactor;
        float yColorB = colorB * yLightFactor;
        float zColorR = colorR * zLightFactor;
        float zColorG = colorG * zLightFactor;
        float zColorB = colorB * zLightFactor;
        float alpha = 0.75f;

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(3.14f));
        matrices.scale(0.8f, 0.75f, 0.8f);
        matrices.translate(0.15f, -1.15f, -1.15f);

        this.light = light;
        VertexConsumer translucentBuffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
        this.worldMatrix = matrices.peek();

        // east (x+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 0f, 1f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 0f, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 1f, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 1f, 1f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(1f, 0f, 0f)
                .next();

        // west (x-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0f, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0f, 1f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 1f, 1f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 1f, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();

        // south (z+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0f, 1f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, 1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 0f, 1f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, 1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 1f, 1f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, 1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 1f, 1f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, 1f)
                .next();

        // north (z-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 0f, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0f, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 1f, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 1f, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, -1f)
                .next();

        // up (y+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 1f, 1f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 1f, 1f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 1f, 0f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 1f, 0f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();

        // down (y-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0f, 0f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMinV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f , 0f, 0f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMinV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 1f, 0f, 1f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0f, 1f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        matrices.pop();
    }
}
