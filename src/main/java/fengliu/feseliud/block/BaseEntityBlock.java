package fengliu.feseliud.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class BaseEntityBlock extends BlockWithEntity implements IModBlock {
    public final String name;

    protected BaseEntityBlock(Settings settings, String name) {
        super(settings);
        this.name = name;
    }

    public abstract BlockEntityType<?> getBlockEntityType();
    public abstract BlockEntityTicker<? super BlockEntity> uesTick();

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, this.getBlockEntityType(), this.uesTick());
    }

    @Override
    public String getBlockName() {
        return this.name;
    }
}
