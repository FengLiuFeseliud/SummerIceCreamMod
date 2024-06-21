package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.block.entity.IceCrusherBlockEntity;
import fengliu.feseliud.block.tool.IceCrusherBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class IceCrusherBlockRenderers implements BlockEntityRenderer<IceCrusherBlockEntity>  {
    private final MinecraftClient client = MinecraftClient.getInstance();
    public final BlockEntityRendererFactory.Context ctx;

    public IceCrusherBlockRenderers(BlockEntityRendererFactory.Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void render(IceCrusherBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = be.getWorld();
        if (world == null){
            return;
        }

        ItemStack cupStack = be.getStack(IceCrusherBlockEntity.CUP_SLOT);
        if (!cupStack.isEmpty()){
            BlockState state = world.getBlockState(be.getPos());
            if (state.isAir()){
                return;
            }

            matrices.push();
            matrices.scale(0.7f, 0.7f, 0.7f);
            Direction direction = state.get(IceCrusherBlock.FACING);
            if (direction == Direction.NORTH){
                matrices.translate(0.71f, 0.14f, 0.55f);
            } else if (direction == Direction.WEST){
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotation(1.6f));
                matrices.translate(0.71f, 0.14f, -0.58f);
            } else if (direction == Direction.EAST){
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(1.6f));
                matrices.translate(-0.71f, 0.14f, 0.86f);
            } else if (direction == Direction.SOUTH){
                matrices.multiply(RotationAxis.POSITIVE_Y.rotation(3.2f));
                matrices.translate(-0.67f, 0.14f, -0.9f);
            }
            client.getItemRenderer().renderItem(cupStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                    client.getItemRenderer().getModel(cupStack, world, null, 0));
            matrices.pop();
        }

        ItemStack iceStack = be.getStack(IceCrusherBlockEntity.ICE_SLOT);
        if (iceStack.isEmpty()){
            return;
        }

        matrices.scale(0.55f, 1.2f, 0.55f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.55f));
        matrices.translate(0.75f, 0.62f, -0.35f);

        matrices.push();
        for (int index = 0; index < iceStack.getCount() - 1; index = index + 2){
            if (index > iceStack.getCount()){
                break;
            }
            if (index != 0 && index % 4 == 0){
                matrices.pop();
                matrices.translate(0f, 0f, -0.03f);
                matrices.push();
            }

            client.getItemRenderer().renderItem(iceStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                    client.getItemRenderer().getModel(iceStack, world, null, 0));
            matrices.translate(0.32f, 0f, 0f);

            if (index + 1 > iceStack.getCount()){
                break;
            }
            client.getItemRenderer().renderItem(iceStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                    client.getItemRenderer().getModel(iceStack, world, null, 0));
            matrices.translate(-0.32f, 0.32f, 0f);
        }
        matrices.pop();
    }
}
