package fengliu.feseliud.utils;

import fengliu.feseliud.block.FacingEntityBlock;
import fengliu.feseliud.block.icecream.IceCreamBarMoldBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

import java.text.DecimalFormat;

public interface IHitSlot{
    int getIndex();
    int getX1();
    int getX2();
    int getZ1();
    int getZ2();

    default boolean isHitSlot(BlockHitResult hit, Direction direction){
        DecimalFormat df= new DecimalFormat("0.00");
        int hitX = Integer.parseInt(String.valueOf(df.format(hit.getPos().getX())).split("\\.")[1]);
        int hitZ = Integer.parseInt(String.valueOf(df.format(hit.getPos().getZ())).split("\\.")[1]);
        if (direction == Direction.WEST || direction == Direction.EAST){
            return (hitX > this.getZ2() && hitX < this.getZ1()) && (hitZ > this.getX2() && hitZ < this.getX1());
        }

        return (hitX > this.getX2() && hitX < this.getX1()) && (hitZ > this.getZ2() && hitZ < this.getZ1());
    }

    static IHitSlot getHitSlot(BlockHitResult hit, BlockState state, IHitSlot[] hitSlots, IHitSlot[] hitSlots2){
        Direction direction = state.get(FacingEntityBlock.FACING).getOpposite();
        if (direction == Direction.WEST || direction == Direction.SOUTH){
            for (IHitSlot slots: hitSlots2){
                if (slots.isHitSlot(hit, direction)){
                    return slots;
                }
            }
            return null;
        }

        for (IHitSlot slots: hitSlots){
            if (slots.isHitSlot(hit, direction)){
                return slots;
            }
        }
        return null;
    }
}
