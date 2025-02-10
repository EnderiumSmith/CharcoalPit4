package charcoalPit.tile;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.recipe.CrusherRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

/*public class TileCrusher extends BlockEntity {

    public EnergyStorage battery;
    public FluidTank output;
    public ItemStackHandler inventory;
    public int process,total;
    public ItemStack previous;
    public boolean isWorking;

    public TileCrusher(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.CRUSHER.get(), pos, blockState);
        output=new FluidTank(16000){
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
        inventory=new ItemStackHandler(2){

            @Override
            public void setStackInSlot(int slot, ItemStack stack) {
                if(slot==0&&previous.isEmpty()){
                    previous=inventory.getStackInSlot(0).copy();
                }
                super.setStackInSlot(slot, stack);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if(slot==0&&previous.isEmpty()){
                    previous=inventory.getStackInSlot(0).copy();
                }
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        battery=new EnergyStorage(32000,80){
            @Override
            public int receiveEnergy(int toReceive, boolean simulate) {
                setChanged();
                return super.receiveEnergy(toReceive, simulate);
            }
        };
        process=0;
        total=0;
        previous=ItemStack.EMPTY;
        isWorking=false;
    }

    public void tick(){
        if(total>0&&!previous.isEmpty()){
            if (inventory.getStackInSlot(0).getItem() != previous.getItem()) {
                total = 0;
                process = 0;
            }
            previous = ItemStack.EMPTY;
        }
        if(total>0){
            if(process<total){
                if(battery.getEnergyStored()>=20){
                    process++;
                    battery.extractEnergy(20,false);
                    if(!isWorking){
                        setActive(true);
                        isWorking=true;
                    }
                    setChanged();
                }else if(isWorking){
                    isWorking=false;
                    setActive(false);
                }
            }
            if(process>=total){
                //done cooking
                CrusherRecipe recipe=CrusherRecipe.getRecipe(inventory.getStackInSlot(0),level);
                if(recipe!=null){
                    ItemStack container=inventory.getStackInSlot(0).getCraftingRemainingItem().copy();
                    inventory.extractItem(0,1,false);
                    container=inventory.insertItem(0,container,false);
                    if(!container.isEmpty()){
                        Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), container);
                    }
                    if(!recipe.fluidOut.isEmpty()){
                        output.fill(new FluidStack(recipe.fluidOut.getStacks()[0].getFluid(),recipe.amountOut), IFluidHandler.FluidAction.EXECUTE);
                    }
                    if(!recipe.itemOut.isEmpty()){
                        inventory.insertItem(1,recipe.itemOut.copy(),false);
                    }
                }
                process=0;
                total=0;
                setChanged();
                CrusherRecipe recipe1=CrusherRecipe.getRecipe(inventory.getStackInSlot(0),level);
                if(validateRecipe(recipe1)){
                    total=recipe1.processTime;
                }else{
                    setActive(false);
                }
            }
        }else{
            CrusherRecipe recipe=CrusherRecipe.getRecipe(inventory.getStackInSlot(0),level);
            if(validateRecipe(recipe)){
                total=recipe.processTime;
                setChanged();
            }
            if(isWorking){
                isWorking=false;
                setActive(false);
            }
        }
    }

    public void setActive(boolean active){
        if(getBlockState().getValue(BlockStateProperties.LIT)!=active) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, active), 3);
        }
    }

    public void dropInventory(){
        if(!inventory.getStackInSlot(0).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(0));
        if(!inventory.getStackInSlot(1).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(1));
    }

    public boolean validateRecipe(CrusherRecipe recipe){
        if(recipe!=null){
            if(!recipe.itemOut.isEmpty()){
                if(!inventory.insertItem(1,recipe.itemOut,true).isEmpty())
                    return false;
            }
            if(!recipe.fluidOut.isEmpty()){
                if(output.fill(new FluidStack(recipe.fluidOut.getStacks()[0].getFluid(),recipe.amountOut), IFluidHandler.FluidAction.SIMULATE)!=recipe.amountOut)
                    return false;
            }
            return true;
        }
        return false;
    }

    public final IItemHandler items=new IItemHandler() {
        @Override
        public int getSlots() {
            return 2;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return slot==0?inventory.insertItem(0,stack,simulate):stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot==1?inventory.extractItem(1,amount,simulate):ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot==0;
        }
    };

    public final IFluidHandler fluids=new IFluidHandler() {
        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return output.getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return 16000;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return output.drain(resource,action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return output.drain(maxDrain,action);
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",inventory.serializeNBT(registries));
        tag.put("output",output.writeToNBT(registries, new CompoundTag()));
        tag.putInt("process",process);
        tag.putInt("total",total);
        tag.putBoolean("running",isWorking);
        tag.put("battery",battery.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
        output.readFromNBT(registries,tag.getCompound("output"));
        process=tag.getInt("process");
        total=tag.getInt("total");
        isWorking=tag.getBoolean("running");
        battery.deserializeNBT(registries,tag.get("battery"));
    }
}*/
