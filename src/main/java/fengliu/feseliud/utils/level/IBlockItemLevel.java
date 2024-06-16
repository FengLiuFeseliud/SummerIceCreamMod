package fengliu.feseliud.utils.level;

import fengliu.feseliud.block.IModBlock;
import net.minecraft.block.Block;

public interface IBlockItemLevel extends IItemLevel{
    IModBlock getBlock();
}
