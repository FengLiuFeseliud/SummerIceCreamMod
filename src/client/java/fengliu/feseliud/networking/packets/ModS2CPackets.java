package fengliu.feseliud.networking.packets;

import fengliu.feseliud.networking.BlockEntitySync;
import fengliu.feseliud.utils.IdUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModS2CPackets {
    public static final Identifier SYNC_INVENTORY = IdUtil.get("sync_inventory");

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SYNC_INVENTORY, BlockEntitySync::syncInventory);
    }
}
