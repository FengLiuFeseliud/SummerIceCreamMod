package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.block.entity.CoolerBoxBlockEntity;
import fengliu.feseliud.block.icecream.CoolerBoxBlock;
import fengliu.feseliud.utils.IHitSlot;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class CoolerBoxBlockEntityRenderer implements BlockEntityRenderer<CoolerBoxBlockEntity> {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public CoolerBoxBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    @Override
    public void render(CoolerBoxBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!(be.getWorld() instanceof ClientWorld world)){
            return;
        }

        BlockState state = world.getBlockState(be.getPos());
        if (state.isAir() || state.get(CoolerBoxBlock.CLOSE)){
            return;
        }
        matrices.scale(1.18f, 1.19f, 1.19f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.57f));

        Direction direction = state.get(CoolerBoxBlock.FACING);
        if (direction == Direction.NORTH){
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(2.35f));
            matrices.translate(-0.145f, -0.875f, -0.1f);
        } else if (direction == Direction.SOUTH){
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(0.8f));
            matrices.translate(-0.162f, 0.323f, -0.1f);
        } else if (direction == Direction.EAST){
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotation(2.35f));
            matrices.translate(-0.747f, -0.27f, -0.1f);
        } else {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(0.8f));
            matrices.translate(0.445f, -0.285f, -0.1f);
        }

        IHitSlot.splitInput(be, IHitSlot.ThreeHitSlot.values()).forEach(inventory -> {
            matrices.push();
            inventory.stacks.forEach(stack -> {
                client.getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, overlay,
                        client.getItemRenderer().getModel(stack, be.getWorld(), null, 0));
                matrices.translate(0f, 0f, -0.033f);
            });
            matrices.pop();
            matrices.translate(0.15f, 0.15f, 0);
        });
    }
}
