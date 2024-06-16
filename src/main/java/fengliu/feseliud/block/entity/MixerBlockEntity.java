package fengliu.feseliud.block.entity;

import fengliu.feseliud.SummerIceCream;
import fengliu.feseliud.block.icecream.IceCreamBlock;
import fengliu.feseliud.fluid.ModFluids;
import fengliu.feseliud.item.icecream.IIceCreamLevelItem;
import fengliu.feseliud.recipes.ListRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MinecartItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MixerBlockEntity extends InventoryBlockEntity {
    public static final String USE_TICK_KEY = "use_tick_key";
    public static final int BASE_STACK_SLOT = 0;
    public static final int MAX_RECIPE_SIZE = 9;
    public static final int MIXER_UES_TICK = 5 * 20;
    private int uesTick = 0;

    public MixerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.MIXER_BLOCK_ENTITY, pos, state);
        this.setMaxItemStack(1);
    }

    public ItemStack getBaseStack(){
        return this.getStack(BASE_STACK_SLOT);
    }

    public void setBaseStack(ItemStack stack){
        this.setStack(BASE_STACK_SLOT, stack);
        this.markDirty();
    }

    public boolean canOutputAndAddUesTick(){
        this.uesTick ++;
        return this.uesTick >= MixerBlockEntity.MIXER_UES_TICK;
    }

    public List<ItemEntity> getMixerInsideItemEntities(World world, BlockPos pos){
        List<ItemEntity> itemEntities = world.getEntitiesByClass(ItemEntity.class, new Box(pos), itemEntity -> true);
        if (itemEntities.size() > MAX_RECIPE_SIZE - 1){
            return itemEntities.subList(0, MAX_RECIPE_SIZE - 2);
        }
        return itemEntities;
    }

    public SimpleInventory getRecipesInventory(ItemStack prevStack, List<ItemEntity> itemEntities){
        SimpleInventory simpleInventory = new SimpleInventory(MAX_RECIPE_SIZE);
        if (prevStack.isEmpty()){
            simpleInventory.addStack(this.getBaseStack());
        } else {
            simpleInventory.addStack(prevStack);
        }
        itemEntities.forEach(itemEntity -> simpleInventory.addStack(itemEntity.getStack()));
        return simpleInventory;
    }

    public SimpleInventory getRecipesInventory(List<ItemEntity> itemEntities){
        return this.getRecipesInventory(ItemStack.EMPTY, itemEntities);
    }

    public ItemStack getRecipeStack(ItemStack prevStack, List<ItemEntity> itemEntities, World world){
        if (itemEntities.isEmpty()){
            return ItemStack.EMPTY;
        }

        Optional<ListRecipes> match = world.getRecipeManager().getFirstMatch(ListRecipes.Type.INSTANCE, this.getRecipesInventory(prevStack, itemEntities), world);
        if (match.isPresent()){
            return match.get().getResult().copy();
        }
        return ItemStack.EMPTY;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (world.isClient() || !(blockEntity instanceof MixerBlockEntity be)){
            return;
        }

        ItemStack baseStack = be.getBaseStack();
        if (baseStack.isEmpty()){
            return;
        }

        List<ItemEntity> itemEntities = be.getMixerInsideItemEntities(world, pos);
        ItemStack recipeStack = ItemStack.EMPTY;
        while (true){
           ItemStack stack = be.getRecipeStack(recipeStack, itemEntities, world);
           if (stack.isEmpty()){
               break;
           }
            recipeStack = stack;
        }

        if (recipeStack.isEmpty() || recipeStack.getItem() instanceof IIceCreamLevelItem){
            be.uesTick = 0;
            return;
        }

        if (!be.canOutputAndAddUesTick()){
            return;
        }

        itemEntities.forEach(itemEntity -> itemEntity.getStack().decrement(1));
        be.setBaseStack(recipeStack);
        be.uesTick = 0;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(USE_TICK_KEY, this.uesTick);
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.uesTick = nbt.getInt(USE_TICK_KEY);
        this.markDirty();
    }
}
