package fengliu.feseliud.utils.level;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.util.Identifier;

/**
 * 等级物品
 */
public interface ILevel {
    /**
     * 获取等级物品等级
     * @return 等级
     */
    int getLevel();

    /**
     * 获取等级物品最大等级
     * @return 最大等级
     */
    int getMaxLevel();

    /**
     * 获取等级物品等级增益
     * @return 增益
     */
    int getGain();

    /**
     * 获取等级物品名字 (物品 Id 冒号后面)
     * @return 等级物品名字
     */
    String getName();

    /**
     * 获取等级物品完整名字 (加上等级)
     * @return 等级物品路径
     */
    default String getIdName(){
        return this.getName() + "_lv_" + this.getLevel();
    }

    /**
     * 获取等级物品 Id
     * @return 等级物品 Id
     */
    default Identifier getId(){
        return IdUtil.get(this.getIdName());
    };
}
