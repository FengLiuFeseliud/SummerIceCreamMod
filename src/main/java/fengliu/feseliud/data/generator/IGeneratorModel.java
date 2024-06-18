package fengliu.feseliud.data.generator;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.util.Identifier;

public interface IGeneratorModel {
    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    String getTextureName();

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    String getPrefixedPath();

    /**
     * 获取纹理路径 id
     * @return 路径id
     */
    default Identifier getTexturePath(){
        return IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath());
    }
}
