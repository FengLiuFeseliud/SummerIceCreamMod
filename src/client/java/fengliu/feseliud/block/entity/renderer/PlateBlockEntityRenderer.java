package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.block.entity.PlateBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public PlateBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    private void tenItemLayerRenderer(int index, ItemStack slotStack, float y, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, World world){
        if (index == 2) {
            matrices.pop();
            matrices.push();
            matrices.translate(0.36f, 0.45f, y);
        } else if (index == 5){
            matrices.translate(-0.97f, 0.28f, 0f);
        } else if(index == 8){
            matrices.pop();
            matrices.push();
            matrices.translate(0.535f, 0.95f, y);
        }

        client.getItemRenderer().renderItem(slotStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                client.getItemRenderer().getModel(slotStack, world, null, 0));
        matrices.translate(0.32f, 0f, 0f);
    }

    @Override
    public void render(PlateBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = be.getWorld();
        matrices.scale(0.73f, 2.5f, 0.7f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.57f));

        float y = -0.04f;
        matrices.push();
        matrices.translate(0.52f, 0.2f, y);

        int slot = 0;
        int index = 0;
        while (slot < 20) {
            if (slot == 10) {
                index = 0;
                matrices.pop();
                y = y - 0.03f;
                matrices.push();
                matrices.translate(0.52f, 0.2f, y);
            }

            ItemStack stack = be.getStack(slot);
            if (stack.isEmpty()){
                matrices.pop();
                return;
            }

            this.tenItemLayerRenderer(index, stack, y, matrices, vertexConsumers, light, overlay, world);
            slot ++;
            index ++;
        }
        matrices.pop();

        y = y - 0.03f;
        matrices.push();
        matrices.translate(0.52f, 0.4f, y);
        for (; slot < 24; slot++){
            if (slot == 22){
                matrices.pop();
                matrices.push();
                matrices.translate(0.52f, 0.75f, y);
            }
            ItemStack slotStack = be.getStack(slot);
            client.getItemRenderer().renderItem(slotStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                    client.getItemRenderer().getModel(slotStack, world, null, 0));
            matrices.translate(0.32f, 0f, 0f);
        }
        matrices.pop();

        y = y - 0.03f;
        matrices.push();
        matrices.translate(0.52f, 0.59f, y);
        for (; slot < 26; slot++){
            ItemStack slotStack = be.getStack(slot);
            if (slotStack.isEmpty()){
                matrices.pop();
                return;
            }
            client.getItemRenderer().renderItem(slotStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                    client.getItemRenderer().getModel(slotStack, world, null, 0));
            matrices.translate(0.32f, 0f, 0f);
        }
        matrices.pop();

        y = y - 0.03f;
        matrices.translate(0.67f, 0.59f, y);
        ItemStack slotStack = be.getStack(slot);
        if (slotStack.isEmpty()){
            return;
        }
        client.getItemRenderer().renderItem(slotStack, ModelTransformationMode.GROUND , false, matrices, vertexConsumers, light, overlay,
                client.getItemRenderer().getModel(slotStack, world, null, 0));
    }
}
