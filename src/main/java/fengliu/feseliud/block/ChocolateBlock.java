package fengliu.feseliud.block;

import fengliu.feseliud.item.ModItems;
import fengliu.feseliud.item.icecream.brick.ChocolateBrick;
import fengliu.feseliud.utils.level.IFoodItemLevel;
import fengliu.feseliud.utils.level.ILevel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ChocolateBlock extends FacingBlock implements IDetachableBlock{
    private final ILevel level;

    public ChocolateBlock(Settings settings, String name, ILevel level) {
        super(settings, name);
        this.level = level;
    }

    @Override
    public ILevel getItemLevel() {
        return this.level;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<ChocolateBrick, IFoodItemLevel> getLevelBrickItems() {
        return ModItems.CHOCOLATE_BRICKS;
    }

    @Override
    public String getBlockName() {
        return this.getItemLevel().getIdName();
    }

    @Override
    public String getTextureName() {
        return this.getBlockName();
    }

    @Override
    public Identifier getTexturePath() {
        return ModBlocks.CHOCOLATE_LIQUID_BLOCK.getTexturePath();
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!(world instanceof ServerWorld serverWorld)) {
            super.afterBreak(world, player, pos, state, blockEntity, tool);
            return;
        }

        if (!EnchantmentHelper.fromNbt(tool.getEnchantments()).containsKey(Enchantments.SILK_TOUCH) || !tool.isIn(ItemTags.SHOVELS)){
            super.afterBreak(world, player, pos, state, blockEntity, tool);
            return;
        }

        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005f);

        Block.dropStack(world, pos, this.asItem().getDefaultStack());
        state.onStacksDropped(serverWorld, pos, tool, true);
    }
}
