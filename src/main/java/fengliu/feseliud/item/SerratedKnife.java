package fengliu.feseliud.item;

import fengliu.feseliud.block.ModBlocks;
import fengliu.feseliud.utils.IdUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SerratedKnife extends BaseItem{
    public SerratedKnife(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (world.isClient() || !miner.isPlayer()){
            return super.postMine(stack, world, state, pos, miner);
        }

        if (!(state.getBlock() instanceof IceBlock)){
            return super.postMine(stack, world, state, pos, miner);
        }

        stack.damage(1, world.getRandom(), (ServerPlayerEntity) miner);
        return true;
    }

    @Override
    public void generateModel(ItemModelGenerator itemModelGenerator) {
        Models.HANDHELD.upload(ModelIds.getItemModelId(this), TextureMap.layer0(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath())), itemModelGenerator.writer);
    }
}
