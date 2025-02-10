package charcoalPit.tile;

import charcoalPit.DataComponents.BloomData;
import charcoalPit.block.BlockBellows;
import charcoalPit.block.BlockBloomeryChimney;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.dataMap.DataMapRegistry;
import charcoalPit.dataMap.FuelTemperatureData;
import charcoalPit.recipe.BloomeryRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.Optional;

public class TileBloomery extends BlockEntity {

    public ItemStackHandler inventory;
    public int progress,processTotal,burnTime,burnTotal,temperature,minTemp,baseTemp,blastBoost;
    public ItemStack previous;


    public TileBloomery(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.BLOOMERY.get(), pos, blockState);
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

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if(slot==0){
                    return BloomeryRecipe.getRecipe(stack,getLevel())!=null;
                }
                if(slot==1){
                    return isFuel(stack);
                }
                return true;
            }
        };
        progress=0;
        processTotal=0;
        burnTime=0;
        burnTotal=0;
        temperature=20;
        minTemp=0;
        baseTemp=20;
        blastBoost=0;
        previous=ItemStack.EMPTY;
    }

    public void tick(){
        if(processTotal>0&&!previous.isEmpty()){
            if (inventory.getStackInSlot(0).getItem() != previous.getItem()) {
                processTotal = 0;
                progress = 0;
            }
            previous = ItemStack.EMPTY;
        }
        if(processTotal>0){
            if(progress<processTotal){
                if(burnTime>0){
                    if(temperature>=minTemp){
                        progress++;
                    }
                }else if(progress>0){
                    progress--;
                }
            }
            if(progress>=processTotal){
                //done smelting
                BloomeryRecipe recipe= getRecipe();
                if(recipe!=null){
                    if(inventory.getStackInSlot(2).isEmpty()){
                        ItemStack stack=new ItemStack(ItemRegistry.BLOOM.get());
                        stack.set(DataComponentRegistry.BLOOM_DATA.get(),new BloomData(recipe.getResult().copy(),1,recipe.getXp()));
                        inventory.insertItem(2,stack,false);
                        inventory.extractItem(0,1,false);
                    }else{
                        ItemStack stack=inventory.getStackInSlot(2);
                        BloomData component=stack.get(DataComponentRegistry.BLOOM_DATA.get());
                        ItemStack nested=component.stack().copy();
                        nested.grow(recipe.getResult().getCount());
                        stack.set(DataComponentRegistry.BLOOM_DATA,new BloomData(nested,component.work()+1,component.xp()+recipe.getXp()));
                        inventory.extractItem(0,1,false);
                    }
                }
                processTotal=0;
                progress=0;
                setChanged();
                int time=trySmelt();
                if(time>0){
                    progress=0;
                    processTotal=time-1;
                }
            }
        }else{
            int time=trySmelt();
            if(time>0){
                progress=0;
                processTotal=time-1;
                if(burnTime>0)
                    setActive(true);
                setChanged();
            }
        }
        //handle temperature
        int targetTemp=baseTemp+200*blastBoost;
        if(burnTime<=0)
            targetTemp=20;
        if(temperature<targetTemp)
            temperature=targetTemp;
        else if(temperature>targetTemp)
            temperature--;
        //handle fuel
        if(burnTime>0){
            burnTime--;
            if(burnTime%20==0)
                setChanged();
            if(burnTime<=0){
                if(processTotal>0){
                    tryBurn();
                }
                if(burnTime<=0){
                    setActive(false);
                    temperature=20;
                    baseTemp=20;
                }
            }
        }else if(processTotal>0){
            tryBurn();
            if(burnTime>0)
                setActive(true);
        }
    }

    public void calculateBlastBoost(){
        int boost=0;
        for(Direction dir:Direction.Plane.HORIZONTAL){
            if(dir==getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)){
                continue;
            }
            BlockState bellow=getLevel().getBlockState(getBlockPos().relative(dir));
            if(bellow.getBlock() instanceof BlockBellows && bellow.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite()==dir && bellow.getValue(BlockStateProperties.POWERED)){
                boost++;
            }
        }
        blastBoost=Math.min(boost,2);
    }

    public void dropInventory(){
        for(int i=0;i<3;i++){
            if(!inventory.getStackInSlot(i).isEmpty()){
                Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(),getBlockPos().getZ(),inventory.getStackInSlot(i).copy());
            }
        }
    }

    public void setActive(boolean active){
        if(getBlockState().getValue(BlockStateProperties.LIT)!=active) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, active), 3);
            BlockPos above=getBlockPos().above();
            BlockState state=getLevel().getBlockState(above);
            if(state.getBlock() instanceof BlockBloomeryChimney){
                getLevel().setBlock(above,state.setValue(BlockStateProperties.LIT,active),3);
            }
        }
    }

    public boolean isFuel(ItemStack stack){
        Holder<Item> holder=stack.getItemHolder();
        return holder.getData(DataMapRegistry.FUEL_TEMPERATURE)!=null;
    }

    public void tryBurn(){
        Holder<Item> holder=inventory.getStackInSlot(1).getItemHolder();
        FuelTemperatureData temperatureData=holder.getData(DataMapRegistry.FUEL_TEMPERATURE);
        FurnaceFuel furnaceFuel=holder.getData(NeoForgeDataMaps.FURNACE_FUELS);
        if(temperatureData!=null&&furnaceFuel!=null&&isFuelHotEnough(temperatureData)){
            burnTime=furnaceFuel.burnTime()/2;
            burnTotal=burnTime;
            baseTemp=temperatureData.temperature();
            temperature=Math.max(temperature,baseTemp);
            ItemStack container=ItemStack.EMPTY;
            if(inventory.getStackInSlot(1).hasCraftingRemainingItem()){
                container=inventory.getStackInSlot(1).getCraftingRemainingItem();
            }
            inventory.extractItem(1,1,false);
            if(!container.isEmpty()){
                container=inventory.insertItem(1,container,false);
                if(!container.isEmpty()){
                    Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(),getBlockPos().getZ(),container);
                }
            }
        }
    }

    public boolean isFuelHotEnough(FuelTemperatureData data){
        return data.temperature()+200*blastBoost>=minTemp||(data.temperature()>=baseTemp&&temperature>=minTemp);
    }

    public int trySmelt(){
        BloomeryRecipe recipe= getRecipe();
        if(recipe!=null){
            if(inventory.getStackInSlot(2).isEmpty()){
                minTemp=recipe.getTemperature();
                return recipe.getCookingTime();
            }else{
                BloomData nested=inventory.getStackInSlot(2).get(DataComponentRegistry.BLOOM_DATA.get());
                if(nested.stack().getItem()==recipe.getResult().getItem()&&nested.stack().getCount()+recipe.getResult().getCount()<=8){
                    minTemp=recipe.getTemperature();
                    return recipe.getCookingTime();
                }

            }
        }
        return 0;
    }

    public BloomeryRecipe getRecipe(){
        return BloomeryRecipe.getRecipe(inventory.getStackInSlot(0),level);
    }

    public final IItemHandler out=new IItemHandler() {
        @Override
        public int getSlots() {
            return 3;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return slot<2?inventory.insertItem(slot,stack,simulate):stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot==2?inventory.extractItem(slot,amount,simulate):ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot<2?inventory.isItemValid(slot,stack):false;
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",inventory.serializeNBT(registries));
        tag.putInt("progress",progress);
        tag.putInt("processTotal",processTotal);
        tag.putInt("burnTime",burnTime);
        tag.putInt("burnTotal",burnTotal);
        tag.putInt("temperature",temperature);
        tag.putInt("minTemp",minTemp);
        tag.putInt("baseTemp",baseTemp);
        tag.putInt("blastBoost",blastBoost);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress=tag.getInt("progress");
        processTotal=tag.getInt("processTotal");
        burnTime=tag.getInt("burnTime");
        burnTotal=tag.getInt("burnTotal");
        temperature=tag.getInt("temperature");
        minTemp=tag.getInt("minTemp");
        baseTemp=tag.getInt("baseTemp");
        blastBoost=tag.getInt("blastBoost");
    }
}
