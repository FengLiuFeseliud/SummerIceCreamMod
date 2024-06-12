package fengliu.feseliud.utils;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.fluid.BaseFluid;
import fengliu.feseliud.item.BaseColorItem;
import fengliu.feseliud.item.IModItem;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
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

public class RegisterUtil {
    public static final List<BaseColorItem> baseColorItems = new ArrayList<>();

    public static <I extends IModItem> I registerItem(Identifier id, I item, RegistryKey<ItemGroup> group){
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(((Item) item).getDefaultStack()));
        return (I) Registry.register(Registries.ITEM, id, (Item) item);
    }

    public static <I extends IModItem> I registerItem(I item, RegistryKey<ItemGroup> group){
        return RegisterUtil.registerItem(IdUtil.get(item.getItemName()), item, group);
    }

    public interface colorItem{
        BaseColorItem get(DyeColor dyeColor);
    }

    public static <I extends BaseColorItem> List<I> registerColorItems(DyeColor[] dyeColors, colorItem colorItem, RegistryKey<ItemGroup> group){
        List<I> items = new ArrayList<>();
        for (DyeColor dyeColor: dyeColors){
            @SuppressWarnings("unchecked")
            I item = (I) colorItem.get(dyeColor);
            baseColorItems.add(item);
            items.add(RegisterUtil.registerItem(IdUtil.get(item.getItemName()), item, group));
        }
        return items;
    }

    public static RegistryKey<ItemGroup> registerItemGroup(String name, ItemGroup itemGroup){
        Identifier id = IdUtil.get(name);
        Registry.register(Registries.ITEM_GROUP, id, itemGroup);
        return  RegistryKey.of(RegistryKeys.ITEM_GROUP, id);
    }

    /**
     * 实例所有等级物品
     * @param levels 等级物品数组
     * @return 等级物品列表
     */
    @SuppressWarnings("unchecked")
    public static <IL extends IItemLevel, I extends IModItem> Map<I, IL> registerItems(IL[] levels, RegistryKey<ItemGroup> group){
        LinkedHashMap<I, IL> items = new LinkedHashMap<>();
        Arrays.stream(levels).forEach(level -> items.put((I) RegisterUtil.registerItem(IdUtil.get(level.getIdName()), level.getItem(), group), level));
        return items;
    }

    public enum FluidType{
        STILL,
        FLOWING;
    }

    public static <F extends BaseFluid> List<F> registerFluids(F fluidStill, F fluidFlowing){
        List<F> fluids = new ArrayList<>();
        fluids.add(Registry.register(Registries.FLUID, IdUtil.getFluidStill(fluidStill.getName()), fluidStill));
        fluids.add(Registry.register(Registries.FLUID, IdUtil.getFluidFlowing(fluidFlowing.getName()), fluidFlowing));
        return fluids;
    }

    @SuppressWarnings("unchecked")
    public static <B extends Block, IB extends IModBlock> B registerBlock(IB block){
        return Registry.register(Registries.BLOCK, block.getId(), (B) block);
    }

    public static <B extends IceCreamBlock, IL extends IIceCreamBlockLevel> Map<IL, B> registerIceCreamBlock(IL[] iceCreamBlockLevels, Function<IL, B> getBlock){
        Map<IL, B> map = new HashMap<>();
        Arrays.stream(iceCreamBlockLevels).forEach(iceCreamBlockLevel -> map.put(iceCreamBlockLevel, registerBlock(getBlock.apply(iceCreamBlockLevel))));
        return map;
    }

    @SuppressWarnings("unchecked")
    public static <BE extends BlockEntity> BlockEntityType<BE> registerBlockEntity(IModBlock block, FabricBlockEntityTypeBuilder.Factory<? extends BE> be){
        return (BlockEntityType<BE>) Registry.register(Registries.BLOCK_ENTITY_TYPE, block.getId(), FabricBlockEntityTypeBuilder.create(be, (Block) block).build(null));
    }

    public static <BE extends BlockEntity> Map<IIceCreamBlockLevel, BlockEntityType<BE>> registerIceCreamBlockEntity(Map<IIceCreamBlockLevel, ? extends IceCreamBlock> blocks, FabricBlockEntityTypeBuilder.Factory<? extends BE> be) {
        Map<IIceCreamBlockLevel, BlockEntityType<BE>> map = new HashMap<>();
        blocks.forEach((iIceCreamBlockLevel, iceCreamBlock) -> map.put(iIceCreamBlockLevel, registerBlockEntity(iceCreamBlock, be)));
        return map;
    }
}
