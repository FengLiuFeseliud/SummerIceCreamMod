package fengliu.feseliud.item;

import fengliu.feseliud.utils.IdUtil;
import fengliu.feseliud.utils.RegisterUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> ITEM_GROUP = RegisterUtil.registerItemGroup(
            "item_group",
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.BAR))
                    .displayName(Text.translatable(IdUtil.getItemGroupName("item_group")))
                    .build()
    );

    public static final RegistryKey<ItemGroup> FOOD_GROUP = RegisterUtil.registerItemGroup(
            "food_group",
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.ICE_CREAM_BAR.keySet().stream().toList().get(0)))
                    .displayName(Text.translatable(IdUtil.getItemGroupName("food_group")))
                    .build()
    );

    public static final RegistryKey<ItemGroup> MATERIALS_GROUP = RegisterUtil.registerItemGroup(
            "materials_group",
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.MILK_ICE_CREAM_LIQUID_BUCKET))
                    .displayName(Text.translatable(IdUtil.getItemGroupName("materials_group")))
                    .build()
    );
}
