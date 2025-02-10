package charcoalPit.tile;

import charcoalPit.block.BlockBellows;
import charcoalPit.block.BlockBloomeryChimney;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.dataMap.DataMapRegistry;
import charcoalPit.dataMap.FuelTemperatureData;
import charcoalPit.recipe.BlastFurnaceRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class TileBlastFurnace extends BlockEntity {

    public static final int ORE=0,FLUX=1,FUEL=2,RESULT=3;

    public ItemStackHandler inventory;
    public int progress,processTotal,burnTime,burnTotal,temperature,minTemp,baseTemp,blastBoost;
    public float xp;
    public ItemStack previous,flux_previous;

    public TileBlastFurnace(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.BLAST_FURNACE.get(), pos, blockState);
        inventory=new ItemStackHandler(4){

            @Override
            public void setStackInSlot(int slot, ItemStack stack) {
                if(slot==ORE&&previous.isEmpty()){
                    previous=stack.copy();
                }
                if(slot==FLUX&&flux_previous.isEmpty()){
                    flux_previous=stack.copy();
                }
                super.setStackInSlot(slot, stack);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if(slot==ORE&&previous.isEmpty()){
                    previous=inventory.getStackInSlot(ORE).copy();
                }
                if(slot==FLUX&&flux_previous.isEmpty()){
                    flux_previous=inventory.getStackInSlot(FLUX).copy();
                }
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if(slot==ORE||slot==FLUX){
                    return BlastFurnaceRecipe.isIngredientValid(stack,slot==FLUX,getLevel())||BlastFurnaceRecipe.isVanillaValid(stack,slot==FLUX,getLevel());
                }
                if(slot==FUEL){
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
        blastBoost=1;
        xp=0;
        previous=ItemStack.EMPTY;
        flux_previous=ItemStack.EMPTY;
    }

    public void tick(){
        if(processTotal>0&&!previous.isEmpty()){
            if(inventory.getStackInSlot(ORE).getItem()!=previous.getItem()){
                processTotal=0;
                progress=0;
            }
            previous=ItemStack.EMPTY;
        }
        if(processTotal>0&&!flux_previous.isEmpty()){
            if(minTemp>0&&inventory.getStackInSlot(FLUX).getItem()!=flux_previous.getItem()){
                processTotal=0;
                progress=0;
            }
            flux_previous=ItemStack.EMPTY;
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
                BlastFurnaceRecipe recipe= BlastFurnaceRecipe.getRecipe(inventory.getStackInSlot(ORE),inventory.getStackInSlot(FLUX),level);
                if(recipe!=null){
                    inventory.insertItem(RESULT,recipe.getResult().copy(),false);
                    inventory.extractItem(ORE,1,false);
                    inventory.extractItem(FLUX,1,false);
                    xp+=recipe.getXp();
                }else{
                    BlastingRecipe vailla=BlastFurnaceRecipe.getVanillaRecipe(inventory.getStackInSlot(ORE),level);
                    if(vailla!=null){
                        inventory.insertItem(RESULT,vailla.getResultItem(null).copy(),false);
                        inventory.extractItem(ORE,1,false);
                        xp+=vailla.getExperience();
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
        blastBoost=Math.min(boost,2)+1;
    }

    public void dropInventory(){
        for(int i=0;i<4;i++){
            if(!inventory.getStackInSlot(i).isEmpty()){
                Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(),getBlockPos().getZ(),inventory.getStackInSlot(i).copy());
            }
        }
        if(xp>0) {
            ExperienceOrb.award((ServerLevel) level, getBlockPos().getCenter(), (int) xp);
            if (level.getRandom().nextFloat() < (xp - (int) xp)) {
                ExperienceOrb.award((ServerLevel) level, getBlockPos().getCenter(), 1);
            }
        }
    }

    public void setActive(boolean active){
        if(getBlockState().getValue(BlockStateProperties.LIT)!=active) {
            getLevel().setBlock(getBlockPos(), getBlockState().setValue(BlockStateProperties.LIT, active), 3);
            BlockPos above=getBlockPos().above(2);
            BlockState state=getLevel().getBlockState(above);
            if(state.getBlock() instanceof BlockBloomeryChimney){
                getLevel().setBlock(above,state.setValue(BlockStateProperties.LIT,active),3);
            }
        }
    }

    public boolean isFuel(ItemStack stack){
        Holder<Item> holder=stack.getItemHolder();
        return holder.getData(DataMapRegistry.FUEL_TEMPERATURE)!=null&&holder.getData(DataMapRegistry.FUEL_TEMPERATURE).clean();
    }

    public void tryBurn(){
        Holder<Item> holder=inventory.getStackInSlot(FUEL).getItemHolder();
        FuelTemperatureData temperatureData=holder.getData(DataMapRegistry.FUEL_TEMPERATURE);
        FurnaceFuel furnaceFuel=holder.getData(NeoForgeDataMaps.FURNACE_FUELS);
        if(temperatureData!=null&&furnaceFuel!=null&&isFuelHotEnough(temperatureData)){
            burnTime=furnaceFuel.burnTime()/2;
            burnTotal=burnTime;
            baseTemp=temperatureData.temperature();
            temperature=Math.max(temperature,baseTemp);
            ItemStack container=ItemStack.EMPTY;
            if(inventory.getStackInSlot(FUEL).hasCraftingRemainingItem()){
                container=inventory.getStackInSlot(FUEL).getCraftingRemainingItem();
            }
            inventory.extractItem(FUEL,1,false);
            if(!container.isEmpty()){
                container=inventory.insertItem(FUEL,container,false);
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
        BlastFurnaceRecipe recipe= BlastFurnaceRecipe.getRecipe(inventory.getStackInSlot(ORE),inventory.getStackInSlot(FLUX),level);
        if(recipe!=null){
            if(inventory.getStackInSlot(RESULT).isEmpty()||
                    (inventory.getStackInSlot(RESULT).getItem()==recipe.getResult().getItem()&&
                            inventory.getStackInSlot(RESULT).getCount()+recipe.getResult().getCount()<=recipe.getResult().getMaxStackSize())){
                minTemp=recipe.getTemperature();
                return recipe.getCookingTime();
            }
        }else{
            BlastingRecipe vanilla=BlastFurnaceRecipe.getVanillaRecipe(inventory.getStackInSlot(ORE),level);
            if(vanilla!=null){
                ItemStack result=vanilla.getResultItem(null);
                if(inventory.getStackInSlot(RESULT).isEmpty()||
                        (inventory.getStackInSlot(RESULT).getItem()==result.getItem()&&
                                inventory.getStackInSlot(RESULT).getCount()+result.getCount()<=result.getMaxStackSize())){
                    minTemp=-1;
                    return (4*vanilla.getCookingTime())/5;
                }
            }
        }
        return 0;
    }

    public final IItemHandler out=new IItemHandler() {
        @Override
        public int getSlots() {
            return 4;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return slot<RESULT?inventory.insertItem(slot,stack,simulate):stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return slot==RESULT?inventory.extractItem(slot,amount,simulate):ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot<RESULT?inventory.isItemValid(slot,stack):false;
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
        tag.putFloat("xp",xp);
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
        xp=tag.getFloat("xp");
    }
}
