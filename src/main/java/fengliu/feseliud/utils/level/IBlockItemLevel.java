package fengliu.feseliud.utils.level;

import fengliu.feseliud.block.IModBlock;

/**
 * 方块物品等级
 */
public interface IBlockItemLevel extends IItemLevel{
    /**
     * 获取方块
     * @return {@link IModBlock} Mod 方块
     */
    IModBlock getBlock();
}
