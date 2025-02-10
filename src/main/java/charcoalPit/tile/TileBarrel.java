package charcoalPit.tile;

import charcoalPit.core.ItemRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.recipe.BarrelRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.*;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TileBarrel extends BlockEntity {

    public FluidTank tank;
    public ItemStackHandler input, output;
    public int process,total;
    boolean valid;

    public TileBarrel(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.BARREL.get(), pos, blockState);
        tank=new FluidTank(16000, f->f.getFluidType().getTemperature()<450&&!f.is(Tags.Fluids.GASEOUS)){
            @Override
            protected void onContentsChanged() {
                total=0;
                valid=false;
                setChanged();
            }
        };
        input=new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                total=0;
                valid=false;
                setChanged();
            }
        };
        output=new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        process=0;
        total=0;
        valid=true;
    }

    public void tick(){
        if(!valid){
            valid=true;
            BarrelRecipe recipe2=BarrelRecipe.getRecipe(input.getStackInSlot(0),tank.getFluid(),level);
            if(validateRecipe(recipe2)>0){
                process=0;
                total=recipe2.processTime-1;
                setChanged();
            }
        }
        if(total>0){
            process++;
            if(process%200==0)
                setChanged();
            if(process>=total){
                BarrelRecipe recipe=BarrelRecipe.getRecipe(input.getStackInSlot(0),tank.getFluid(),level);
                if(recipe!=null){
                    int rounds=validateRecipe(recipe);
                    boolean has_output_item=!recipe.itemOut.isEmpty();
                    boolean has_output_fluid=!recipe.fluidOut.isEmpty();
                    tank.drain(rounds*recipe.amountIn, FluidAction.EXECUTE);
                    if(has_output_fluid) {
                        tank.setFluid(FluidStack.EMPTY);
                        tank.fill(new FluidStack(recipe.fluidOut.getStacks()[0].getFluid(), rounds*recipe.amountOut), FluidAction.EXECUTE);
                    }
                    ItemStack container=input.getStackInSlot(0).getCraftingRemainingItem().copy();
                    container.setCount(rounds*recipe.itemAmount);
                    input.extractItem(0, rounds*recipe.itemAmount, false);
                    container=input.insertItem(0,container,false);
                    if(!container.isEmpty()){
                        Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), container);
                    }
                    if(has_output_item) {
                        output.insertItem(0, new ItemStack(recipe.itemOut.getItemHolder(), rounds*recipe.itemOut.getCount(), recipe.itemOut.getComponentsPatch()), false);
                    }
                }
                process=0;
                total=0;
                setChanged();
                BarrelRecipe recipe1=BarrelRecipe.getRecipe(input.getStackInSlot(0),tank.getFluid(),level);
                if(validateRecipe(recipe1)>0){
                    process=0;
                    total=recipe1.processTime-1;
                }
            }
        }else{
            //process fluid container
            transferFluid();
            fillGlassBottle();
        }
    }

    public int validateRecipe(BarrelRecipe recipe){
        if(recipe==null)
            return 0;
        boolean void_excess_input_fluid=recipe.excessFluid;
        boolean void_excess_output_item=recipe.excessItem;
        boolean has_output_item=!recipe.itemOut.isEmpty();
        boolean has_output_fluid=!recipe.fluidOut.isEmpty();
        if(!recipe.fluidIn.test(tank.getFluid()))
            return 0;
        if(!recipe.itemIn.test(input.getStackInSlot(0)))
            return 0;
        //inputs valid;
        FluidTank sim_tank=new FluidTank(16000);
        FluidTank sim_fluid=new FluidTank(16000);
        sim_fluid.setFluid(tank.getFluid().copy());
        if(has_output_fluid&&
                FluidStack.isSameFluid(tank.getFluid(),new FluidStack(recipe.fluidOut.getStacks()[0].getFluid(),recipe.amountOut))){
            sim_tank.setFluid(tank.getFluid());
        }
        ItemStackHandler sim_in=new ItemStackHandler(1);
        sim_in.setStackInSlot(0,input.getStackInSlot(0).copy());
        ItemStackHandler sim_out=new ItemStackHandler(1);
        sim_out.setStackInSlot(0,output.getStackInSlot(0).copy());
        int rounds=0;
        boolean once_inserted_item=false;
        boolean ok;
        do {
            ok=true;
            if(sim_fluid.getFluidAmount()< recipe.amountIn)
                ok=false;
            else
                sim_fluid.drain(recipe.amountIn, FluidAction.EXECUTE);
            if(sim_in.getStackInSlot(0).isEmpty()||sim_in.getStackInSlot(0).getCount()<recipe.itemAmount)
                ok=false;
            else
                sim_in.extractItem(0, recipe.itemAmount, false);
            if(has_output_fluid) {
                if(sim_tank.fill(new FluidStack(recipe.fluidOut.getStacks()[0].getFluid(), recipe.amountOut), FluidAction.EXECUTE)< recipe.amountOut)
                    ok=false;
            }
            if(has_output_item) {
                if(sim_out.insertItem(0, recipe.itemOut.copy(), false)!=ItemStack.EMPTY) {
                    if(!once_inserted_item||!void_excess_output_item) {
                        ok=false;
                    }
                }else
                    once_inserted_item=true;
            }
            if(ok)
                rounds++;
        }while(ok);
        if(rounds==0)
            return 0;
        if(has_output_fluid&&!void_excess_input_fluid&&tank.getFluidAmount()!= recipe.amountIn*rounds)
            return 0;
        return rounds;
    }

    public void transferFluid() {
        FluidActionResult empty= FluidUtil.tryEmptyContainer(input.getStackInSlot(0), tank, Integer.MAX_VALUE, null, false);
        if(empty.success) {
            if(empty.getResult()!=null&&!empty.getResult().isEmpty()) {
                if(output.insertItem(0, empty.getResult(), true).isEmpty()) {
                    //all fits
                    output.insertItem(0, FluidUtil.tryEmptyContainer(input.getStackInSlot(0), tank, Integer.MAX_VALUE, null, true).getResult(), false);
                    input.extractItem(0, 1, false);
                }
            }else {
                //no container
                FluidUtil.tryEmptyContainer(input.getStackInSlot(0), tank, Integer.MAX_VALUE, null, true);
                input.extractItem(0, 1, false);
            }
        }else {
            FluidActionResult fill=FluidUtil.tryFillContainer(input.getStackInSlot(0), tank, Integer.MAX_VALUE, null, false);
            if(fill.success) {
                if(output.insertItem(0, fill.getResult(), true).isEmpty()) {
                    //all fits
                    output.insertItem(0, FluidUtil.tryFillContainer(input.getStackInSlot(0), tank, Integer.MAX_VALUE, null, true).getResult(), false);
                    input.extractItem(0, 1, false);
                }
            }else {
                //fail
            }
        }
    }

    public void fillGlassBottle(){
        if(input.getStackInSlot(0).getItem()== Items.GLASS_BOTTLE&&tank.getFluidAmount()>=250&&tank.getFluid().getFluidType()== FluidRegistry.ALCOHOL.fluidType.get()){
            if(output.insertItem(0,new ItemStack(ItemRegistry.ALCOHOL_BOTTLE.get()),false).isEmpty()){
                tank.drain(250,FluidAction.EXECUTE);
                input.extractItem(0,1,false);
            }
        }
        if(input.getStackInSlot(0).getItem()== Items.GLASS_BOTTLE&&tank.getFluidAmount()>=250&&tank.getFluid().getFluidType()== FluidRegistry.ETHANOL.fluidType.get()){
            if(output.insertItem(0,new ItemStack(ItemRegistry.ETHANOL_BOTTLE.get()),false).isEmpty()){
                tank.drain(250,FluidAction.EXECUTE);
                input.extractItem(0,1,false);
            }
        }
        if(input.getStackInSlot(0).getItem()==ItemRegistry.ALCOHOL_BOTTLE.get()){
            if(output.insertItem(0,new ItemStack(Items.GLASS_BOTTLE),true).isEmpty()&&tank.fill(new FluidStack(FluidRegistry.ALCOHOL.source,250),FluidAction.SIMULATE)>=250){
                output.insertItem(0,new ItemStack(Items.GLASS_BOTTLE),false);
                input.extractItem(0,1,false);
                tank.fill(new FluidStack(FluidRegistry.ALCOHOL.source,250),FluidAction.EXECUTE);
            }
        }
        if(input.getStackInSlot(0).getItem()==ItemRegistry.ETHANOL_BOTTLE.get()){
            if(output.insertItem(0,new ItemStack(Items.GLASS_BOTTLE),true).isEmpty()&&tank.fill(new FluidStack(FluidRegistry.ETHANOL.source,250),FluidAction.SIMULATE)>=250){
                output.insertItem(0,new ItemStack(Items.GLASS_BOTTLE),false);
                input.extractItem(0,1,false);
                tank.fill(new FluidStack(FluidRegistry.ETHANOL.source,250),FluidAction.EXECUTE);
            }
        }
    }

    public void dropContents(){
        if(!input.getStackInSlot(0).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), input.getStackInSlot(0));
        if(!output.getStackInSlot(0).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), output.getStackInSlot(0));
    }

    public final IItemHandler out=new IItemHandler() {
        @Override
        public int getSlots() {
            return 2;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return slot==0?input.getStackInSlot(0):output.getStackInSlot(0);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return slot==0?input.insertItem(0,stack,simulate):stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot==1?output.extractItem(0,amount,simulate):ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return slot==0?input.getSlotLimit(0):output.getSlotLimit(0);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot==0?input.isItemValid(0,stack):false;
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("tank",tank.writeToNBT(registries, new CompoundTag()));
        tag.put("input",input.serializeNBT(registries));
        tag.put("output",output.serializeNBT(registries));
        tag.putInt("process",process);
        tag.putInt("total",total);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tank.readFromNBT(registries,tag.getCompound("tank"));
        input.deserializeNBT(registries,tag.getCompound("input"));
        output.deserializeNBT(registries,tag.getCompound("output"));
        process=tag.getInt("process");
        total=tag.getInt("total");
    }
}
