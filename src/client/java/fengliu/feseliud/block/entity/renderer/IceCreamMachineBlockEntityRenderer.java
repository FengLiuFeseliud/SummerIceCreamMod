package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.block.entity.IceCreamMachineBlockEntity;
import fengliu.feseliud.block.tool.IceCreamMachineBlock;
import fengliu.feseliud.item.icecream.liquid.FoodLiquidBucket;
import fengliu.feseliud.mixin.MixinBucketItemAccessor;
import fengliu.feseliud.utils.IHitSlot;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.block.BlockState;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class IceCreamMachineBlockEntityRenderer implements BlockEntityRenderer<IceCreamMachineBlockEntity> {
    // countHeight
    public static final float COUNT_HEIGHT = 0.337f / 9;
    private static final float yLightFactor = 0.5f;
    private static final float zLightFactor = 0.8f;
    private static final float xLightFactor = 0.6f;

    private final MinecraftClient client = MinecraftClient.getInstance();

    public IceCreamMachineBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    private void fluidRender(Fluid fluid, World world, BlockPos pos, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int count, int tick){
        if (fluid.equals(Fluids.EMPTY)){
            return;

        }
        Sprite[] sprites =  FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(world, pos, fluid.getDefaultState());
        int color = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidColor(world, pos, fluid.getDefaultState());
        Sprite topSprite = sprites[0];
        Sprite sideSprite = sprites[1];

        float colorR = (float) (color >> 16 & 255) / 255.0F;
        float colorG = (float) (color >> 8 & 255) / 255.0F;
        float colorB = (float) (color & 255) / 255.0F;
        float zColorR = colorR * 0.8f;
        float zColorG = colorG * 0.8f;
        float zColorB = colorB * 0.8f;
        float alpha = 1f;

        VertexConsumer translucentBuffer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        MatrixStack.Entry worldMatrix = matrices.peek();

        float height = COUNT_HEIGHT * count - (COUNT_HEIGHT / IceCreamMachineBlockEntity.USE_TICK * tick);

        // up (y+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0.2f)
                .color(zColorR, zColorR, zColorR, alpha)
                .texture(topSprite.getMinU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.2f, height, 0.2f)
                .color(zColorR, zColorR, zColorR, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.2f, height, 0f)
                .color(zColorR, zColorR, zColorR, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0f)
                .color(zColorR, zColorR, zColorR, alpha)
                .texture(topSprite.getMinU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();

        // north (z-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.2f, 0f, 0f)
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
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.2f, height, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
    }

    private void fluidOutputRender(Fluid fluid, World world, BlockPos pos, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int tick){
        if (fluid.equals(Fluids.EMPTY)){
            return;

        }
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
        float alpha = 1f;

        VertexConsumer translucentBuffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
        MatrixStack.Entry worldMatrix = matrices.peek();

        float height = 0.35f;
        float downY = 0.35f - 0.35f / 30 * Math.min(tick, 30);

        // east (x+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, downY, 0.05f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, downY, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, height, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, height, 0.05f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(1f, 0f, 0f)
                .next();

        // west (x-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, downY, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, downY, 0.05f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0.05f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0f)
                .color(xColorR, xColorG, xColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(-1f, 0f, 0f)
                .next();

        // south (z+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, downY, 0.05f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, 1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, downY, 0.05f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, 1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, height, 0.05f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, 1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0.05f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, 1f)
                .next();

        // north (z-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, downY, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, downY, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getMinV())
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMaxU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, -1f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, height, 0f)
                .color(zColorR, zColorG, zColorB, alpha)
                .texture(sideSprite.getMinU(), sideSprite.getFrameV(8))
                .light(light)
                .normal(0f, 0f, -1f)
                .next();

        // up (y+)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0.05f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, height, 0.05f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMinV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, height, 0f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, height, 0f)
                .color(yColorR, yColorG, yColorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, 1f, 0f)
                .next();

        // down (y-)
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, downY, 0f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMinV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, downY, 0f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMinV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0.05f, downY, 0.05f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMaxU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
        translucentBuffer
                .vertex(worldMatrix.getPositionMatrix(), 0f, downY, 0.05f)
                .color(colorR, colorG, colorB, alpha)
                .texture(topSprite.getMinU(), topSprite.getMaxV())
                .light(light)
                .normal(0f, -1f, 0f)
                .next();
    }

    @Override
    public void render(IceCreamMachineBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = be.getWorld();
        if (world == null){
            return;
        }

        BlockState state = world.getBlockState(be.getPos());
        if (state.isAir()){
            return;
        }

        Direction direction = state.get(IceCreamMachineBlock.FACING);
        if (direction == Direction.EAST){
            matrices.multiply(RotationAxis.NEGATIVE_Y.rotation(1.6F));
            matrices.translate(-0.02F, 0F, -1.02F);
        }

        if (direction == Direction.WEST){
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(1.6F));
            matrices.translate(-1.01F, 0F, -0.02F);
        }

        if (direction == Direction.SOUTH){
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(3.2F));
            matrices.translate(-0.955F, 0F, -1.03F);
        }

        IHitSlot[] hitSlots = IHitSlot.reverse(IceCreamMachineBlock.HIT_SLOTS);

        matrices.push();
        matrices.scale(0.8f, 0.8f, 1f);
        matrices.translate(0.43f, 0.23f, 0.25f);
        for (IHitSlot hitSlot: hitSlots){
            ItemStack slotStack = be.getStack(hitSlot.getInventorySlot(be, IceCreamMachineBlockEntity.CUP_SLOT));
            if (!slotStack.isEmpty()){
                client.getItemRenderer().renderItem(slotStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                        client.getItemRenderer().getModel(slotStack, world, null, 0));
            }
            matrices.translate(0.39f, 0f, 0f);
        }
        matrices.pop();

        int hitSlotIndex = 0;
        for(IHitSlot hitSlot: hitSlots){
            ItemStack liquidStack = hitSlot.getInventoryLastStack(be);
            if (liquidStack.isEmpty() || !(liquidStack.getItem() instanceof FoodLiquidBucket)){
                hitSlotIndex++;
                continue;
            }

            int tick = be.propertyDelegate.get(hitSlot.getIndex());

            matrices.push();
            matrices.translate(0.24f + 0.24f * hitSlotIndex + (float) hitSlotIndex / 100, 0.6f, 0.15f);
            matrices.scale(1f, 1f, 1f);
            this.fluidRender(
                    ((MixinBucketItemAccessor) liquidStack.getItem()).getFluid(),
                    world, be.getPos(), matrices, vertexConsumers, light,
                    hitSlot.getHitSlotInventory(be).count(liquidStack.getItem()), tick);
            matrices.pop();

            matrices.push();
            matrices.translate(0.318f + hitSlotIndex * 0.318f, 0.15f, 0.26f);
            if (tick > 0){
                this.fluidOutputRender(((MixinBucketItemAccessor) liquidStack.getItem()).getFluid(), world, be.getPos(), matrices, vertexConsumers, light, tick);
            }
            matrices.pop();

            hitSlotIndex++;
        }
    }
}
