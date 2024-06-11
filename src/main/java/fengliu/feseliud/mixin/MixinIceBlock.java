package fengliu.feseliud.mixin;

import fengliu.feseliud.block.IncompleteIce;
import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceBlock.class)
public abstract class MixinIceBlock extends Block {
    public MixinIceBlock(Settings settings) {
        super(settings);
    }

    @Unique
    private BlockState smallerIceBlock(ItemStack tool, BlockState state, PlayerEntity player){
        if (tool.isIn(ItemTags.PICKAXES)){
            return Blocks.AIR.getDefaultState();
        }

        if (state.isOf(Blocks.ICE)){
            return ModBlocks.INCOMPLETE_ICE_STAIRS_BLOCK.getDefaultState().with(IncompleteIce.FACING, player.getMovementDirection().rotateClockwise(Direction.Axis.Y));
        }

        if (state.isOf(ModBlocks.INCOMPLETE_ICE_STAIRS_BLOCK)){
            return ModBlocks.INCOMPLETE_ICE_SLAB_BLOCK.getDefaultState();
        }
        return Blocks.AIR.getDefaultState();
    }

    @Inject(
            method = "afterBreak",
            at = @At("HEAD"),
            cancellable = true
    )
    public void breakDropCrushedIce(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack tool, CallbackInfo ci) {
        if (EnchantmentHelper.get(tool).containsKey(Enchantments.SILK_TOUCH)){
            return;
        }

        if (!tool.isIn(ItemTags.PICKAXES) && !tool.isOf(ModItems.SERRATED_KNIFE)){
            return;
        }

        Item dropItem;
        if (tool.isIn(ItemTags.PICKAXES)){
            dropItem = (Item) ModItems.CRUSHED_ICE.keySet().toArray()[0];
        } else {
            dropItem = (Item) ModItems.ICE_CUBES.keySet().toArray()[0];
        }

        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005f);

       BlockState smallerIceBlockState = this.smallerIceBlock(tool, state, player);
       if (state.isOf(Blocks.ICE) && smallerIceBlockState.isAir()){
           dropStack(world, pos, new ItemStack(dropItem, 4));
       } else if (state.isOf(ModBlocks.INCOMPLETE_ICE_SLAB_BLOCK)){
           dropStack(world, pos, new ItemStack(dropItem, 2));
       } else {
           dropStack(world, pos, new ItemStack(dropItem, 1));
       }

       world.setBlockState(pos, smallerIceBlockState);
       ci.cancel();
    }
}
