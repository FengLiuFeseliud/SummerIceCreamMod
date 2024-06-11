package fengliu.feseliud.block.icecream;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.block.entity.CookieIceCreamBlockEntity;
import fengliu.feseliud.block.entity.ModBlockEntitys;
import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.block.BaseBlockItem;
import fengliu.feseliud.item.block.icecream.IIceCreamBlockLevel;
import fengliu.feseliud.item.block.icecream.IceCream;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.item.icecream.bar.IceCreamBar;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CookieIceCreamBlock extends IceCreamBlock {
    public CookieIceCreamBlock(Settings settings, String name, IIceCreamBlockLevel iIceCreamBlockLevel) {
        super(settings, name, iIceCreamBlockLevel);
    }

    @Override
    public Map<IceCreamBar, IIceCreamLevel> getIceCreams() {
        return ModItems.COOKIE_ICE_CREAM_BRICKS;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.COOKIE_ICE_CREAM_BLOCK_ENTITYS.get(this.getIceCreamBlockLevel());
    }

    @Override
    public Identifier getTexturePath() {
        return ModBlocks.COOKIE_ICE_CREAM_LIQUID_BLOCK.getTexturePath();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CookieIceCreamBlockEntity(pos, state);
    }
}
