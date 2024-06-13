package fengliu.feseliud.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface MixinLivingEntity {

    @Invoker("applyFoodEffects")
    void invokerApplyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity);
}
