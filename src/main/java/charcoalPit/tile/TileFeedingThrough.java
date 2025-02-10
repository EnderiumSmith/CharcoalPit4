package charcoalPit.tile;

import charcoalPit.block.BlockFeedingThrough;
import charcoalPit.core.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class TileFeedingThrough extends BlockEntity {

    public ItemStackHandler inventory;
    public int time;
    public AABB area;
    public boolean changed=false;

    public TileFeedingThrough(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.FEEDING_THROUGH.get(), pos, blockState);
        inventory=new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                changed=true;
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
            time=0;
            if(!inventory.getStackInSlot(0).isEmpty()){
                List<Animal> list=level.getEntitiesOfClass(Animal.class,area,animal -> animal.isFood(inventory.getStackInSlot(0)));
                if(!list.isEmpty() && list.size()<16){
                    float chance=list.size()>2?0.66F:1F;
                    for(Animal animal:list){
                        if(!animal.isBaby() && animal.canFallInLove() && animal.getAge()==0){
                            boolean rotated=getBlockState().getValue(BlockFeedingThrough.ROTATED);
                            Path path1=animal.getNavigation().createPath(rotated?getBlockPos().north():getBlockPos().west(),1);
                            Path path2=animal.getNavigation().createPath(rotated?getBlockPos().south():getBlockPos().east(),1);
                            if((path1!=null && path1.canReach())||(path2!=null && path2.canReach())){
                                if(animal.getRandom().nextFloat()<chance){
                                    if(animal instanceof AbstractHorse horse){
                                        horse.fedFood(null,inventory.getStackInSlot(0));
                                    }else
                                        animal.setInLove(null);
                                }
                                inventory.extractItem(0,1,false);
                                if(inventory.getStackInSlot(0).isEmpty())
                                    break;
                            }
                        }
                    }
                }
            }
        }
        if(changed){
            if(getBlockState().getValue(BlockFeedingThrough.HAS_BAY) == inventory.getStackInSlot(0).isEmpty()){
                level.setBlockAndUpdate(getBlockPos(),getBlockState().setValue(BlockFeedingThrough.HAS_BAY,!inventory.getStackInSlot(0).isEmpty()));
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
