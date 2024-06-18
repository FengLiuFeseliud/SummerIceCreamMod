package fengliu.feseliud.block;

import fengliu.feseliud.data.generator.IGeneratorModel;
import fengliu.feseliud.data.generator.LootTablesGeneration;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;


/**
 * 方块注册
 */
public interface IModBlock extends IGeneratorModel {
    String PREFIXED_PATH = "block/";

    /**
     * 方块 Id 字符串
     * @return Id
     */
    String getBlockName();

    /**
     * 方块 Id
     * @return Id
     */
    default Identifier getId() {
        return IdUtil.get(this.getBlockName());
    }

    @Override
    default String getPrefixedPath() {
        return PREFIXED_PATH;
    }

    /**
     * 获取模型 Id
     * @return 模型 Id
     */
    default Identifier getModelId() {
        return IdUtil.get(this.getPrefixedPath() + this.getTextureName());
    }

    /**
     * 生成方块状态, 参考 {@link fengliu.feseliud.data.generator.ModelDataGeneration#generateBlockStateModels(BlockStateModelGenerator)}
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    default void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create((Block) this,
                        BlockStateVariant.create().put(VariantSettings.MODEL, this.getModelId())));
    }

    /**
     * 生成方块模型, 参考 {@link fengliu.feseliud.data.generator.ModelDataGeneration#generateItemModels(ItemModelGenerator)}
     * @param blockStateModelGenerator 方块状态模型生成器
     */
    default void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        // 指向父级模型
        Identifier pathId = this.getTexturePath();
        new Model(Optional.of(pathId), Optional.empty())
                .upload((Block) this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
    }

    /**
     * 用于生成战利品 json, 参考 {@link LootTablesGeneration#generate()}
     * @param lootTablesGeneration 战利品生成器
     */
    default void generateLootItem(LootTablesGeneration lootTablesGeneration){
        if (this instanceof FluidBlock){
            return;
        }
        lootTablesGeneration.addDrop((Block) this);
    }
}
