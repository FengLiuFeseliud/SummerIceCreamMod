package fengliu.feseliud.block;

import fengliu.feseliud.data.generator.LootTablesGeneration;
import fengliu.feseliud.utils.level.ILevel;
import fengliu.feseliud.utils.level.ILevelItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.Map;

/**
 * 可拆解方块, 块 -> 楼梯 -> 台阶
 */
public interface IDetachableBlock extends IModBlock {
    VoxelShape NORTH_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0, 0.5, 0.5, 1, 1,1));
    VoxelShape SOUTH_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0, 0.5, 0, 1, 1,0.5));
    VoxelShape WEST_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0.5, 0.5, 0, 1, 1,1));
    VoxelShape EAST_SHAPE = VoxelShapes.union(VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1), VoxelShapes.cuboid(0, 0.5, 0, 0.5, 1,1));

    /**
     * 方块物品等级
     * @return 物品等级
     */
    ILevel getItemLevel();

    /**
     * 块掉落的物品
     * @return 物品组
     */
    <LI extends ILevelItem, I extends ILevel> Map<LI, I> getLevelBrickItems();

    /**
     * 获取方块形状
     * @param state 方块状态
     * @return 方块形状
     */
    default VoxelShape getShape(BlockState state){
        int level = this.getItemLevel().getLevel();
        if (level == 1){
            return VoxelShapes.cuboid(0, 0, 0, 1, 1,1);
        } else if (level == 2){
            return FacingBlock.getFacingShape(state, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE);
        }
        return VoxelShapes.cuboid(0, 0, 0, 1, 0.5,1);
    }

    @Override
    default void generateLootItem(LootTablesGeneration lootTablesGeneration){
        int level = 5 - this.getItemLevel().getLevel();
        lootTablesGeneration.addDrop((Block) this, LootTable.builder().pool(LootPool.builder()
                .with(ItemEntry.builder((Item) this.getLevelBrickItems().keySet().toArray()[0])
                        .apply((SetCountLootFunction.builder(ConstantLootNumberProvider.create(level)))))
        ));
    }

    @Override
    default void generateBlockModel(BlockStateModelGenerator blockStateModelGenerator) {
        int level = this.getItemLevel().getLevel();
        Identifier pathId = this.getTexturePath();

        if (level == 1){
            Models.CUBE_ALL.upload((Block) this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        } else if (level == 2){
            Models.STAIRS.upload((Block) this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        } else if (level == 3){
            Models.SLAB.upload((Block) this, TextureMap.all(pathId), blockStateModelGenerator.modelCollector);
        }
    }

    @Override
    default void generateBlockStateModel(BlockStateModelGenerator blockStateModelGenerator) {
        if (this.getItemLevel().getLevel() != 2){
            IModBlock.super.generateBlockStateModel(blockStateModelGenerator);
            return;
        }

        Identifier modelId = this.getModelId();
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create((Block) this)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING)
                        .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId))
                        .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, modelId).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                )
        );
    }
}
