package fengliu.feseliud.block.entity.renderer;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.block.entity.IceCreamBarMoldBlockEntity;
import fengliu.feseliud.block.icecream.IceCreamBarMoldBlock;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.ModBlockItems;
import fengliu.feseliud.recipes.IceCreamBarMoldRecipes;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;

import java.util.List;
import java.util.Optional;

public class IceCreamBarMoldBlockEntityRenderer implements BlockEntityRenderer<IceCreamBarMoldBlockEntity> {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public IceCreamBarMoldBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

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

        List<SimpleInventory> inventories = IceCreamBarMoldBlockEntity.splitInput(be);
        for (SimpleInventory inventory : inventories) {
            Optional<IceCreamBarMoldRecipes> match = be.getWorld().getRecipeManager().getFirstMatch(IceCreamBarMoldRecipes.Type.INSTANCE, inventory, be.getWorld());
            ItemStack rendererStack;
            if (match.isPresent()) {
                rendererStack = match.get().getResult();
            } else {
                rendererStack = inventory.getStack(0);
            }

            client.getItemRenderer().renderItem(
                    rendererStack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, overlay,
                    client.getItemRenderer().getModel(rendererStack, be.getWorld(), null, 0)
            );
            matrices.translate(-0.175f, -0.165f, 0f);
        }
    }
}
