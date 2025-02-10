package charcoalPit.tile;

import charcoalPit.block.BlockNestingBox;
import charcoalPit.core.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class TileNestingBox extends BlockEntity {

    public ItemStackHandler inventory;
    public int time;
    public AABB area;
    public boolean changed=false;

    public TileNestingBox(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.NESTING_BOX.get(), pos, blockState);
        inventory=new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                changed=true;
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.is(Tags.Items.EGGS);
            }
        };
        time=0;
        area=new AABB(getBlockPos().getX()-4,getBlockPos().getY()-1,getBlockPos().getZ()-4,getBlockPos().getX()+5,getBlockPos().getY()+1,getBlockPos().getZ()+5);
    }

    public void tick(){
        if(time<1200){
            time++;
            if(time%20==0)
                setChanged();
        }else{
            //collect eggs
            time=0;
            if(!inventory.getStackInSlot(0).isEmpty() && inventory.getStackInSlot(0).getCount()>=inventory.getStackInSlot(0).getMaxStackSize()) return;
            List<Chicken> list=level.getEntitiesOfClass(Chicken.class,area);
            if(!list.isEmpty()){
                for(Chicken chicken:list){
                    if(chicken.eggTime<=1200 && !chicken.isBaby() && !chicken.isChickenJockey()){
                        Path path=chicken.getNavigation().createPath(getBlockPos(),1);
                        if(path==null || !path.canReach()) continue;
                        if(inventory.insertItem(0,new ItemStack(Items.EGG),false).isEmpty()){
                            chicken.eggTime+=6000+chicken.getRandom().nextInt(6000);
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        if(changed){
            changed=false;
            int eggs=inventory.getStackInSlot(0).isEmpty()?0:(int)Math.ceil((inventory.getStackInSlot(0).getCount()*4F)/inventory.getStackInSlot(0).getMaxStackSize());
            if(eggs!=getBlockState().getValue(BlockNestingBox.EGGS)){
                level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockNestingBox.EGGS,eggs));
            }
        }
    }

    public void dropInventory(){
        if(!inventory.getStackInSlot(0).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(0));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("time",time);
        tag.put("inventory",inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        time=tag.getInt("time");
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
    }
}
