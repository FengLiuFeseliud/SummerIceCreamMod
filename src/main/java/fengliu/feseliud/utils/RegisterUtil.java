package fengliu.feseliud.utils;

import fengliu.feseliud.block.IDetachableBlock;
import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.item.BaseDyeColorItem;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.utils.color.IColorItem;
import fengliu.feseliud.utils.level.IBlockItemLevel;
import fengliu.feseliud.utils.level.IItemLevel;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Function;

/**
 * 注册 Mod 内容
 */
public class RegisterUtil {
    /**
     * 在 Mod 客户端入口需注册颜色处理器的物品列表, 参考 {@link IColorItem}
     */
    public static final List<IColorItem> baseColorItems = new ArrayList<>();


    /**
     * 注册物品
     * @param id 物品 ID {@link Identifier}
     * @param item 需注册物品
     * @param group 物品所在物品组 {@link ItemGroup}, Mod 物品组参见 {@link fengliu.feseliud.item.ModItemGroups}
     * @return 已注册物品
     * @param <I> {@link IModItem} Mod 物品
     */
    @SuppressWarnings("unchecked")
    public static <I extends IModItem> I registerItem(Identifier id, I item, RegistryKey<ItemGroup> group){
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(((Item) item).getDefaultStack()));
        I regItem =  (I) Registry.register(Registries.ITEM, id, (Item) item);
        if (regItem instanceof IColorItem){
            baseColorItems.add((IColorItem) regItem);
        }
        return regItem;
    }

    /**
     * 注册物品
     * @param item 需注册物品
     * @param group 物品所在物品组 {@link ItemGroup}, Mod 物品组参见 {@link fengliu.feseliud.item.ModItemGroups}
     * @return 已注册物品
     * @param <I> {@link IModItem} Mod 物品
     */
    public static <I extends IModItem> I registerItem(I item, RegistryKey<ItemGroup> group){
        return RegisterUtil.registerItem(IdUtil.get(item.getItemName()), item, group);
    }

    /**
     * 注册使用16色染料物品
     * @param colorItem 需注册物品
     * @param group 物品所在物品组 {@link ItemGroup}, Mod 物品组参见 {@link fengliu.feseliud.item.ModItemGroups}
     * @return 已注册物品
     * @param <I> {@link IModItem} Mod 物品
     */
    public static <I extends BaseDyeColorItem> List<I> registerDyeColorItems(Function<DyeColor, I> colorItem, RegistryKey<ItemGroup> group){
        List<I> items = new ArrayList<>();
        for (DyeColor dyeColor: DyeColor.values()){
            I item = colorItem.apply(dyeColor);
            items.add(RegisterUtil.registerItem(IdUtil.get(item.getItemName()), item, group));
        }
        return items;
    }

    /**
     * 注册所有等级物品
     * @param levels 物品等级组
     * @param group 物品所在物品组 {@link ItemGroup}, Mod 物品组参见 {@link fengliu.feseliud.item.ModItemGroups}
     * @return 已注册等级物品表
     * @param <IL> {@link IItemLevel} Mod 物品等级
     * @param <I> {@link IModItem} Mod 物品
     */
    @SuppressWarnings("unchecked")
    public static <IL extends IItemLevel, I extends IModItem> Map<I, IL> registerItems(IL[] levels, RegistryKey<ItemGroup> group){
        LinkedHashMap<I, IL> items = new LinkedHashMap<>();
        Arrays.stream(levels).forEach(level -> items.put((I) RegisterUtil.registerItem(IdUtil.get(level.getIdName()), level.getItem(), group), level));
        return items;
    }

    /**
     * 注册物品组
     * @param name 物品组 ID 字符串
     * @param itemGroup 物品组
     * @return 已注册物品组
     */
    public static RegistryKey<ItemGroup> registerItemGroup(String name, ItemGroup itemGroup){
        Identifier id = IdUtil.get(name);
        Registry.register(Registries.ITEM_GROUP, id, itemGroup);
        return  RegistryKey.of(RegistryKeys.ITEM_GROUP, id);
    }

    public enum FluidType{
        STILL,
        FLOWING;
    }

    /**
     * 注册液体
     * @param fluidStill 静正液体
     * @param fluidFlowing 流动液体
     * @return 已注册液体组
     * @param <F> {@link BaseFluid} Mod 液体
     */
    public static <F extends BaseFluid> List<F> registerFluids(F fluidStill, F fluidFlowing){
        List<F> fluids = new ArrayList<>();
        fluids.add(Registry.register(Registries.FLUID, IdUtil.getFluidStill(fluidStill.getName()), fluidStill));
        fluids.add(Registry.register(Registries.FLUID, IdUtil.getFluidFlowing(fluidFlowing.getName()), fluidFlowing));
        return fluids;
    }

    /**
     * 注册方块
     * @param block 需注册方块
     * @return 已注册方块
     * @param <B> {@link IModBlock} Mod 方块
     */
    @SuppressWarnings("unchecked")
    public static <B extends IModBlock> B registerBlock(B block){
        return (B) Registry.register(Registries.BLOCK, block.getId(), (Block) block);
    }

    /**
     * 注册所有等级方块
     * @param blockItemLevels 方块等级组
     * @param getBlock 需注册方块
     * @return 已注册方块组
     * @param <B> {@link IDetachableBlock} Mod 可切割方块
     * @param <IL> {@link IBlockItemLevel} Mod 方块等级
     */
    public static <B extends IDetachableBlock, IL extends IBlockItemLevel> Map<IL, B> registerLevelBlocks(IL[] blockItemLevels, Function<IL, B> getBlock){
        Map<IL, B> map = new HashMap<>();
        Arrays.stream(blockItemLevels).forEach(blockItemLevel -> map.put(blockItemLevel, registerBlock(getBlock.apply(blockItemLevel))));
        return map;
    }

    /**
     * 注册方块实体
     * @param block 方块
     * @param be 需注册方块实体
     * @return 已注册方块实体
     * @param <BE> {@link BlockEntity} 方块实体
     */
    @SuppressWarnings("unchecked")
    public static <BE extends BlockEntity> BlockEntityType<BE> registerBlockEntity(IModBlock block, FabricBlockEntityTypeBuilder.Factory<? extends BE> be){
        return (BlockEntityType<BE>) Registry.register(Registries.BLOCK_ENTITY_TYPE, block.getId(), FabricBlockEntityTypeBuilder.create(be, (Block) block).build(null));
    }

    /**
     * 注册所有等级方块实体
     * @param blocks 方块组
     * @param be 方块实体
     * @return 已注册方块实体组
     * @param <BE> {@link BlockEntity} 方块实体
     * @param <IL> {@link IBlockItemLevel} 方块等级
     */
    public static <BE extends BlockEntity, IL extends IBlockItemLevel> Map<IL, BlockEntityType<BE>> registerIceCreamBlockEntity(Map<IL, ? extends IceCreamBlock> blocks, FabricBlockEntityTypeBuilder.Factory<? extends BE> be) {
        Map<IL, BlockEntityType<BE>> map = new HashMap<>();
        blocks.forEach((iIceCreamBlockLevel, iceCreamBlock) -> map.put(iIceCreamBlockLevel, registerBlockEntity(iceCreamBlock, be)));
        return map;
    }
}
