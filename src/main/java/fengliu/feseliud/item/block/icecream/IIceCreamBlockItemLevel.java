package fengliu.feseliud.item.block.icecream;

import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.item.icecream.IIceCreamLevel;
import fengliu.feseliud.utils.level.IBlockItemLevel;

/**
 * 冰淇淋方块物品等级
 */
public interface IIceCreamBlockItemLevel extends IIceCreamLevel, IBlockItemLevel {

    /**
     * 获取冰淇淋方块
     * @return 方块
     */
    @Override
    IceCreamBlock getBlock();
}
