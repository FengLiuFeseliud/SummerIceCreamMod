package fengliu.feseliud.item.block.icecream;

import fengliu.feseliud.block.IModBlock;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.utils.level.IBlockItemLevel;
import net.minecraft.block.Block;

import java.util.List;

public interface IIceCreamBlockLevel extends IIceCreamLevel, IBlockItemLevel {
    @Override
    IceCreamBlock getBlock();
}
