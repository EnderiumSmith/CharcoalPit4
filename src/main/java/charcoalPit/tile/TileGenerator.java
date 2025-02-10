package charcoalPit.tile;

import charcoalPit.core.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

/*public class TileGenerator extends BlockEntity {

    public FluidTank fuel;
    public EnergyStorage buffer;
    public int fire;
    public boolean isRunning;

    public TileGenerator(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.GENERATOR.get(), pos, blockState);
        fuel=new FluidTank(4000,f->isValidFuel(f.getFluid())){
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
        buffer=new EnergyStorage(32000,160);
        isRunning=false;
    }

    private BlockCapabilityCache<IEnergyStorage, Direction> capability;

    public void tick(){
        if(level.hasNeighborSignal(getBlockPos())){
            if(fire<40&&fuel.getFluidAmount()>=100){
                fire+=fuel.getFluid().getFluid().getBucket().builtInRegistryHolder().getData(NeoForgeDataMaps.FURNACE_FUELS).burnTime();
                fuel.drain(100, IFluidHandler.FluidAction.EXECUTE);
                setChanged();
            }
            if(fire>0){
                if(!isRunning){
                    isRunning=true;
                    setActive(true);
                }
                //run engine
                fire-=buffer.receiveEnergy(Math.min(40,fire),false);
                IEnergyStorage output=capability.getCapability();
                if(output!=null){
                    buffer.extractEnergy(output.receiveEnergy(Math.min(160,buffer.getEnergyStored()),false),false);
                }
                setChanged();
            }else{
                if(fire<=0&&isRunning){
                    isRunning=false;
                    setActive(false);
                }
            }
        }else{
            if(isRunning){
                isRunning=false;
                setActive(false);
            }
        }
    }

    public boolean isValidFuel(Fluid fluid){
        Item bucket=fluid.getBucket();
        if(bucket!=null){
            FurnaceFuel ff=bucket.builtInRegistryHolder().getData(NeoForgeDataMaps.FURNACE_FUELS);
            if(ff!=null){
                return ff.burnTime()>0;
            }
        }
        return false;
    }

    public void setActive(boolean active){
        if(getBlockState().getValue(BlockStateProperties.LIT)!=active) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, active), 3);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if(!level.isClientSide)
            capability=BlockCapabilityCache.create(Capabilities.EnergyStorage.BLOCK,(ServerLevel) level,getBlockPos().relative(getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)),getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("fuel",fuel.writeToNBT(registries,new CompoundTag()));
        tag.put("energy",buffer.serializeNBT(registries));
        tag.putInt("fire",fire);
        tag.putBoolean("running",isRunning);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fuel.readFromNBT(registries,tag.getCompound("fuel"));
        buffer.deserializeNBT(registries,tag.get("energy"));
        fire=tag.getInt("fire");
        isRunning=tag.getBoolean("running");
    }
}*/
