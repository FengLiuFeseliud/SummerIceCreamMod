package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.tool.IceCreamBarMoldBlock;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import fengliu.feseliud.mixin.MixinBucketItemAccessor;
import fengliu.feseliud.utils.HitSlot;
import fengliu.feseliud.utils.IHitSlot;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import org.joml.Quaternionf;

import java.util.List;

public class IceCreamBarMoldBlockEntityRenderer implements BlockEntityRenderer<IceCreamBarMoldBlockEntity> {
    private static final float yLightFactor = 0.5f;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public IceCreamBarMoldBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    private void fluidRender(Fluid fluid, IceCreamBarMoldBlockEntity be, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){
        World world = be.getWorld();
        BlockPos pos = be.getPos();

        Sprite topSprite =  FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(world, pos, fluid.getDefaultState())[0];
        int color = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(world, pos, fluid.getDefaultState());

        float colorR = (float) (color >> 16 & 255) / 255.0F;
        float colorG = (float) (color >> 8 & 255) / 255.0F;
        float colorB = (float) (color & 255) / 255.0F;
        float alpha = 1f;

        matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_X.rotation(1.55f));
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotation(0.78F));
        matrices.translate(-0.01f, 0.02f, 0f);

        VertexConsumer translucentBuffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        MatrixStack.Entry worldMatrix = matrices.peek();

        float h = 1f / 16 * 5.5F;
        float w = 1f / 16 * 3.5f;

        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0.01f, h)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), w, 0.01f, h)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), w, 0.01f, 0f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, 0.01f, 0f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        matrices.pop();
    }

    @Override
    public void render(IceCreamBarMoldBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction direction = be.getCachedState().get(IceCreamBarMoldBlock.FACING);
        if (direction == Direction.SOUTH || direction == Direction.NORTH){
            matrices.scale(1.33f, 1f, 1.55f);
        }

        if (direction == Direction.WEST || direction == Direction.EAST){
            matrices.scale(1.56f, 1f, 1.3f);
        }
        matrices.translate(0.21f, 0.12f, 0.65f);
        matrices.multiply(new Quaternionf().rotateX(1.56f).rotateZ(2.36f));

        if (direction == Direction.SOUTH){
            matrices.translate(-0.14f, 0.13f, 0f);
        }

        if (direction == Direction.NORTH){
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(3.14f));
            matrices.translate(0.55f, -0.11f, 0f);
        }

        if (direction == Direction.WEST){
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(1.56f));
            matrices.translate(0.31f, 0.27f, 0f);
        }

        if (direction == Direction.EAST){
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(-1.56f));
            matrices.translate(0.1f, -0.26f, 0f);
        }

        List<SimpleInventory> inventories = IHitSlot.splitInventor(be, HitSlot.ThreeHitSlot.values());
        for (SimpleInventory inventory : inventories) {
            ItemStack rendererStack = inventory.getStack(0);
            if (rendererStack.getItem() instanceof IceCreamBar || rendererStack.isOf(ModItems.BAR)){
                client.getItemRenderer().renderItem(
                        rendererStack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, overlay,
                        client.getItemRenderer().getModel(rendererStack, be.getWorld(), null, 0)
                );
            }

           inventory.stacks.forEach(stack -> {
                if ((stack.getItem() instanceof BucketItem bucketItem) && !((MixinBucketItemAccessor) bucketItem).getFluid().equals(Fluids.EMPTY)){
                    this.fluidRender(((MixinBucketItemAccessor) stack.getItem()).getFluid(), be, matrices, vertexConsumers, light);
                }
            });
            matrices.translate(-0.175f, -0.165f, 0f);
        }
    }
}
