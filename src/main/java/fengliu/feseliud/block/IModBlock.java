package fengliu.feseliud.block;

import fengliu.feseliud.utils.IdUtil;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;


/**
 * 方块注册
 */
public interface IModBlock {
    String PREFIXED_PATH = "block/";

    /**
     * 方块 ID 字符串
     * @return ID 字符串
     */
    String getBlockName();

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    String getTextureName();

    /**
     * 方块 ID
     * @return Identifier
     */
    default Identifier getId(){
        return IdUtil.get(this.getBlockName());
    }

    /**
     * 获取模型 {@link Identifier}
     * @return 模型 Identifier
     */
    default Identifier getModelId(){
        return IdUtil.get(PREFIXED_PATH + this.getTextureName());
    }

    /**
     * 生成方块状态
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    default void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator){
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create((Block) this,
                        BlockStateVariant.create().put(VariantSettings.MODEL, this.getModelId())));
    }

    /**
     * 生成方块模型
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    default void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        // 指向父级模型
        Identifier modelId = this.getModelId();
        new Model(Optional.of(modelId), Optional.empty())
                .upload((Block) this, TextureMap.all(modelId), blockStateModelGenerator.modelCollector);
    }
}

