package fengliu.feseliud.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StatusEffectInstance.class)
public interface MixinStatusEffectInstance{

    /**
     * 设置效果时长
     * @param duration 时长
     */
    @Accessor("duration")
    void setDuration(int duration);
}
