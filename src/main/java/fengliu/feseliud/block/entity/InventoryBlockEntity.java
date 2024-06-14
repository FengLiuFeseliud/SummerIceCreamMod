package fengliu.feseliud.block.entity;

import fengliu.feseliud.networking.packets.ModServerMessage;
import fengliu.feseliud.utils.IInventory;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.List;


public abstract class InventoryBlockEntity extends BlockEntity implements IInventory {
    private DefaultedList<ItemStack> inventory;

    public InventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * 设置库存最大大小
     * @param maxItemStack 库存最大大小
     */
    protected void setMaxItemStack(int maxItemStack){
        inventory = DefaultedList.ofSize(maxItemStack, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    /**
     * 向客户端同步库存
     */
    public void syncInventory(){
        if (this.world.isClient()){
            return;
        }

        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(this.pos);
        data.writeInt(this.getItems().size());

        for(ItemStack stack: this.getItems()){
            data.writeItemStack(stack);
        }

        for (ServerPlayerEntity player: PlayerLookup.tracking((ServerWorld) this.world, this.pos)){
            ServerPlayNetworking.send(player, ModServerMessage.SYNC_INVENTORY, data);
        }
    }

    public void syncResetInventory(PacketByteBuf buf){
        int size = buf.readInt();
        for (int index = 0; index < size; index++){
            this.setStack(index, buf.readItemStack());
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.world == null){
            return;
        }

        if (!this.world.isClient()){
            this.syncAll();
        }
    }

    /**
     * 同步所有数据
     */
    public void syncAll(){
        this.syncInventory();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.markDirty();
    }
}
