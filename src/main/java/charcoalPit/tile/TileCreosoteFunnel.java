package charcoalPit.tile;

import charcoalPit.core.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class TileCreosoteFunnel extends BlockEntity {

    public FluidTank tank;

    public TileCreosoteFunnel(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.CREOSOTE_FUNNEL.get(), pos, blockState);
        tank=new FluidTank(4000){
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
    }

    public void tick(){
        if(level.getGameTime()%20==0 && !tank.isEmpty() && level.hasNeighborSignal(getBlockPos())){
            IFluidHandler cap=level.getCapability(Capabilities.FluidHandler.BLOCK,getBlockPos().above(),Direction.DOWN);
            if(cap!=null){
                tank.drain(cap.fill(tank.getFluid(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }

    public final IFluidHandler out=new IFluidHandler() {
        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int t) {
            return tank.getFluid();
        }

        @Override
        public int getTankCapacity(int t) {
            return 4000;
        }

        @Override
        public boolean isFluidValid(int t, FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return tank.drain(resource,action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return tank.drain(maxDrain,action);
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("tank",tank.writeToNBT(registries,new CompoundTag()));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tank.readFromNBT(registries,tag.getCompound("tank"));
    }
}
