package fengliu.feseliud.data.generator;

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
}
