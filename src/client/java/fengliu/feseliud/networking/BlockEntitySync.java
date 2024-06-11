package fengliu.feseliud.networking;

import fengliu.feseliud.block.entity.InventoryBlockEntity;
import fengliu.feseliud.block.entity.MixerBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class BlockEntitySync {
    public static void syncInventory(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (buf == null) {
            return;
        }

        BlockPos pos = buf.readBlockPos();
        if (!(client.world.getBlockEntity(pos) instanceof InventoryBlockEntity inventoryBlockEntity)) {
            return;
        }

        inventoryBlockEntity.syncResetInventory(buf);
    }
}
