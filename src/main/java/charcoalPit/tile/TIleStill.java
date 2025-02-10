package charcoalPit.tile;

import charcoalPit.block.BlockBloomeryChimney;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.dataMap.DataMapRegistry;
import charcoalPit.recipe.StillRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class TIleStill extends BlockEntity {

    public FluidTank input,output;
    public ItemStackHandler inventory;
    public int process,total,burnTime,burnTotal;
    public ItemStack previous;
    public boolean needsItem;
    //input>1 output>2 fuel>3

    public TIleStill(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.STILL.get(), pos, blockState);
        input=new FluidTank(16000,f->StillRecipe.isValidFluid(f,level)){
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
        output=new FluidTank(16000){
            @Override
            protected void onContentsChanged() {
                setChanged();
            }
        };
        inventory=new ItemStackHandler(3){
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
        process=0;
        total=0;
        burnTime=0;
        burnTotal=0;
        previous=ItemStack.EMPTY;
        needsItem=false;
    }

    public void tick(){
        if(total>0&&!previous.isEmpty()){
            if (needsItem&&inventory.getStackInSlot(0).getItem() != previous.getItem()) {
                total = 0;
                process = 0;
            }
            previous = ItemStack.EMPTY;
        }
        if(total>0){
            if(process<total){
                if(burnTime>0){
                    process++;
                }else if(process>0){
                    process--;
                }
            }
            if(process>=total){
                //done cooking
                StillRecipe recipe=StillRecipe.getRecipe(inventory.getStackInSlot(0),input.getFluid(),level);
                if(recipe!=null){
                    input.drain(recipe.amountIn, IFluidHandler.FluidAction.EXECUTE);
                    if(!recipe.itemIn.isEmpty()){
                        ItemStack container=inventory.getStackInSlot(0).getCraftingRemainingItem().copy();
                        inventory.extractItem(0,recipe.itemAmount,false);
                        container.setCount(recipe.itemAmount);
                        inventory.insertItem(0,container,false);
                        if(!container.isEmpty()){
                            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), container);
                        }
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
                StillRecipe recipe2=StillRecipe.getRecipe(inventory.getStackInSlot(0),input.getFluid(),level);
                if(validateRecipe(recipe2)){
                    total=recipe2.processTime;
                    needsItem=!recipe2.itemIn.isEmpty();
                }
                transferFluid();
            }
        }else{
            StillRecipe recipe=StillRecipe.getRecipe(inventory.getStackInSlot(0),input.getFluid(),level);
            if(validateRecipe(recipe)){
                total=recipe.processTime;
                needsItem=!recipe.itemIn.isEmpty();
                if(burnTime>0){
                    setActive(true);
                }
                setChanged();
            }else
                transferFluid();
        }
        //burn
        if(burnTime>0){
            burnTime--;
            if(burnTime%20==0)
                setChanged();
            if(burnTime<=0){
                if(total>0){
                    tryBurn();
                }
                if(burnTime<=0){
                    setActive(false);
                }
            }
        }else if(total>0){
            tryBurn();
            if(burnTime>0)
                setActive(true);
        }
    }

    public void transferFluid(){
        FluidActionResult empty= FluidUtil.tryEmptyContainer(inventory.getStackInSlot(0), input, Integer.MAX_VALUE, null, false);
        if(empty.success) {
            if(empty.getResult()!=null&&!empty.getResult().isEmpty()) {
                if(FluidUtil.getFluidContained(empty.getResult()).isEmpty()){
                    if(inventory.insertItem(1,empty.getResult(),true).isEmpty()){
                        inventory.insertItem(1,FluidUtil.tryEmptyContainer(inventory.getStackInSlot(0),input,Integer.MAX_VALUE,null,true).getResult(),false);
                        inventory.extractItem(0,1,false);
                    }
                }else{
                    if(inventory.getStackInSlot(0).getCount()==1)
                        inventory.setStackInSlot(0,FluidUtil.tryEmptyContainer(inventory.getStackInSlot(0),input,Integer.MAX_VALUE,null,true).getResult());
                }
            }else{
                FluidUtil.tryEmptyContainer(inventory.getStackInSlot(0), input, Integer.MAX_VALUE, null, true);
                inventory.extractItem(0, 1, false);
            }
        }
    }

    public boolean isFuel(ItemStack stack){
        return stack.getBurnTime(null)>0;
    }

    public void tryBurn(){
        int furnaceFuel=inventory.getStackInSlot(2).getBurnTime(null);
        if(furnaceFuel>0){
            burnTime=furnaceFuel;
            burnTotal=burnTime;
            ItemStack container=ItemStack.EMPTY;
            if(inventory.getStackInSlot(2).hasCraftingRemainingItem()){
                container=inventory.getStackInSlot(2).getCraftingRemainingItem();
            }
            inventory.extractItem(2,1,false);
            if(!container.isEmpty()){
                container=inventory.insertItem(2,container,false);
                if(!container.isEmpty()){
                    Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(),getBlockPos().getZ(),container);
                }
            }
        }
    }

    public void setActive(boolean active){
        if(getBlockState().getValue(BlockStateProperties.LIT)!=active) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, active), 3);
        }
    }

    public boolean validateRecipe(StillRecipe recipe){
        if(recipe!=null){
            if(!recipe.itemIn.isEmpty()){
                if(!(recipe.itemIn.test(inventory.getStackInSlot(0))&&inventory.getStackInSlot(0).getCount()>=recipe.itemAmount)){
                    return false;
                }
            }
            if(!(recipe.fluidIn.test(input.getFluid())&&input.getFluidAmount()>=recipe.amountIn)){
                return false;
            }
            if(!recipe.fluidOut.isEmpty()){
                if(!(output.isEmpty()||(FluidStack.isSameFluid(output.getFluid(),recipe.fluidOut.getStacks()[0])&&output.getCapacity()-output.getFluidAmount()>=recipe.amountOut))){
                    return false;
                }
            }
            if(!recipe.itemOut.isEmpty()){
                if(!(inventory.getStackInSlot(1).isEmpty()||(ItemStack.isSameItem(inventory.getStackInSlot(1),recipe.itemOut)&&inventory.getSlotLimit(1)-inventory.getStackInSlot(1).getCount()>=recipe.itemOut.getCount()))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void dropInventory(){
        if(!inventory.getStackInSlot(0).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(0));
        if(!inventory.getStackInSlot(1).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(1));
        if(!inventory.getStackInSlot(2).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(2));
    }

    public final IFluidHandler fluids=new IFluidHandler() {
        @Override
        public int getTanks() {
            return 2;
        }

        @Override
        public FluidStack getFluidInTank(int i) {
            return i==0?input.getFluidInTank(0):output.getFluidInTank(0);
        }

        @Override
        public int getTankCapacity(int i) {
            return 16000;
        }

        @Override
        public boolean isFluidValid(int i, FluidStack fluidStack) {
            return i==0?input.isFluidValid(fluidStack):false;
        }

        @Override
        public int fill(FluidStack fluidStack, FluidAction fluidAction) {
            return input.fill(fluidStack,fluidAction);
        }

        @Override
        public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
            return output.drain(fluidStack,fluidAction);
        }

        @Override
        public FluidStack drain(int i, FluidAction fluidAction) {
            return output.drain(i,fluidAction);
        }
    };

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
            return slot==0?inventory.insertItem(slot,stack,simulate):stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot==1?inventory.extractItem(slot,amount,simulate):ItemStack.EMPTY;
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

    public final IItemHandler side_items=new IItemHandler() {
        @Override
        public int getSlots() {
            return 2;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot+1);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return slot==1&&isFuel(stack)?inventory.insertItem(2,stack,simulate):stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot==0?inventory.extractItem(1,amount,simulate):ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot+1);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if(slot==1){
                return isFuel(stack);
            }
            return false;
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("input",input.writeToNBT(registries, new CompoundTag()));
        tag.put("inventory",inventory.serializeNBT(registries));
        tag.put("output",output.writeToNBT(registries, new CompoundTag()));
        tag.putInt("process",process);
        tag.putInt("total",total);
        tag.putInt("burnTime",burnTime);
        tag.putInt("burnTotal",burnTotal);
        tag.putBoolean("needsItem",needsItem);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        input.readFromNBT(registries,tag.getCompound("input"));
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
        output.readFromNBT(registries,tag.getCompound("output"));
        process=tag.getInt("process");
        total=tag.getInt("total");
        burnTime=tag.getInt("burnTime");
        burnTotal=tag.getInt("burnTotal");
        needsItem=tag.getBoolean("needsItem");
    }
}
