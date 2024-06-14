package fengliu.feseliud.block.entity;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.item.block.icecream.ChorusFruitIceCream;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import fengliu.feseliud.utils.RegisterUtil;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Map;

public class ModBlockEntitys {
    public static final BlockEntityType<IceCreamBarMoldBlockEntity> ICE_CREAM_BAR_MOLD_BLOCK_ENTITY = RegisterUtil.registerBlockEntity(ModBlocks.ICE_CREAM_BAR_MOLD_BLOCK, IceCreamBarMoldBlockEntity::new);
    public static final BlockEntityType<MixerBlockEntity> MIXER_BLOCK_ENTITY = RegisterUtil.registerBlockEntity(ModBlocks.MIXER_BLOCK, MixerBlockEntity::new);
    public static final Map<IIceCreamBlockLevel, BlockEntityType<IceCreamBlockEntity>> ICE_CREAM_BLOCK_ENTITYS = RegisterUtil.registerIceCreamBlockEntity(ModBlocks.ICE_CREAM_BLOCKS, IceCreamBlockEntity::new);
    public static final Map<IIceCreamBlockLevel, BlockEntityType<ChocolateIceCreamBlockEntity>> CHOCOLATE_ICE_CREAM_BLOCK_ENTITYS = RegisterUtil.registerIceCreamBlockEntity(ModBlocks.CHOCOLATE_ICE_CREAM_BLOCKS, ChocolateIceCreamBlockEntity::new);
    public static final Map<IIceCreamBlockLevel, BlockEntityType<GoldenAppleIceCreamBlockEntity>> GOLDEN_APPLE_ICE_CREAM_BLOCK_ENTITYS = RegisterUtil.registerIceCreamBlockEntity(ModBlocks.GOLDEN_APPLE_ICE_CREAM_BLOCKS, GoldenAppleIceCreamBlockEntity::new);
    public static final Map<IIceCreamBlockLevel, BlockEntityType<ChorusFruitIceCreamBlockEntity>> CHORUS_FRUIT_ICE_CREAM_BLOCK_ENTITYS = RegisterUtil.registerIceCreamBlockEntity(ModBlocks.CHORUS_FRUIT_ICE_CREAM_BLOCKS, ChorusFruitIceCreamBlockEntity::new);
    public static final Map<IIceCreamBlockLevel, BlockEntityType<GlowBerriesIceCreamBlockEntity>> GLOW_BERRIES_CREAM_BLOCK_ENTITYS = RegisterUtil.registerIceCreamBlockEntity(ModBlocks.GLOW_BERRIES_ICE_CREAM_BLOCKS, GlowBerriesIceCreamBlockEntity::new);
    public static final Map<IIceCreamBlockLevel, BlockEntityType<CookieIceCreamBlockEntity>> COOKIE_ICE_CREAM_BLOCK_ENTITYS = RegisterUtil.registerIceCreamBlockEntity(ModBlocks.COOKIE_ICE_CREAM_BLOCKS, CookieIceCreamBlockEntity::new);

    public static void registerAllBlockEntity(){
    }
}
