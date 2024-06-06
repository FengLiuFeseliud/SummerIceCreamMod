package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.entity.BlockEntityType;

public class ModBlockEntitys {
    public static final BlockEntityType<IceCreamBarMoldBlockEntity> ICE_CREAM_BAR_MOLD_BLOCK_ENTITY = RegisterUtil.registerBlockEntity(ModBlocks.ICE_CREAM_BAR_MOLD_BLOCK, IceCreamBarMoldBlockEntity::new);

    public static void registerAllBlockEntity(){

    }
}
