package charcoalPit.tile;

import charcoalPit.core.TileEntityRegistry;
import charcoalPit.recipe.CrusherRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TilePress extends BlockEntity {

    public FluidTank input,output;
    public ItemStackHandler inventory;
    public int process,total,burnTime,burnTotal;
    public ItemStack previous;
    Random rand;
    boolean finish=false;

    public TilePress(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.PRESS.get(), pos, blockState);
        rand=new Random();
        input=new FluidTank(4000, f->f.is(Tags.Fluids.WATER)){
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
        process=0;
        total=0;
        burnTime=0;
        burnTotal=0;
        previous=ItemStack.EMPTY;
    }

    public void tick(){
        finish=false;
        if(total>0&&!previous.isEmpty()){
            if (inventory.getStackInSlot(0).getItem() != previous.getItem()) {
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
                finish=true;
                sendUpdate();
                CrusherRecipe recipe=CrusherRecipe.getRecipe(inventory.getStackInSlot(0),level);
                if(recipe!=null){
                    input.drain(2, IFluidHandler.FluidAction.EXECUTE);
                    inventory.extractItem(0,1, false);
                    output.fill(new FluidStack(recipe.fluidOut.getStacks()[0].getFluid(), recipe.amountOut), IFluidHandler.FluidAction.EXECUTE);
                    doParticles();
                }
                process=0;
                total=0;
                setChanged();
                if(input.getFluidAmount()>=2) {
                    CrusherRecipe recipe2 = CrusherRecipe.getRecipe(inventory.getStackInSlot(0), level);
                    if (validateRecipe(recipe2)) {
                        total = 100;
                    }
                }
            }
        }else{
            if(input.getFluidAmount()>=2) {
                CrusherRecipe recipe = CrusherRecipe.getRecipe(inventory.getStackInSlot(0), level);
                if (validateRecipe(recipe)) {
                    total = 100;
                    if (burnTime > 0) {
                        setActive(true);
                    }
                    setChanged();
                }
            }
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

    public void clientTick(){
        if(finish){
            finish=false;
                for (int i = 0; i < 4; i++) {
                    double x = getBlockPos().getX() + rand.nextFloat();
                    double y = getBlockPos().getY() + 1.125;
                    double z = getBlockPos().getZ() + rand.nextFloat();
                    level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, (0.5F - rand.nextFloat()) / 10, 0.1f + rand.nextFloat() / 8, (0.5F - rand.nextFloat()) / 10);
                }

        }
    }

    public void doParticles(){
        level.playSound(null, getBlockPos(), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.9F,1F);
    }

    public boolean isFuel(ItemStack stack){
        return stack.getBurnTime(null)>0;
    }

    public void tryBurn(){
        Holder<Item> holder=inventory.getStackInSlot(1).getItemHolder();
        int furnaceFuel=inventory.getStackInSlot(1).getBurnTime(null);
        if(furnaceFuel>0){
            burnTime=furnaceFuel;
            burnTotal=burnTime;
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

    public boolean validateRecipe(CrusherRecipe recipe){
        if(recipe!=null){
            return output.isEmpty()||(FluidStack.isSameFluid(output.getFluid(),recipe.fluidOut.getStacks()[0])&&output.getCapacity()-output.getFluidAmount()>=recipe.amountOut);
        }
        return false;
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
            return i==0?input.getCapacity():16000;
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
            if(isItemValid(slot, stack))
                return inventory.insertItem(slot,stack,simulate);
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot==0?CrusherRecipe.isPressItemValid(stack,level):isFuel(stack);
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
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag=super.getUpdateTag(registries);
        tag.putBoolean("finish",finish);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        finish=tag.getBoolean("finish");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        CompoundTag tag=pkt.getTag();
        if(!tag.isEmpty()){
            handleUpdateTag(tag,lookupProvider);
        }
    }

    public void sendUpdate(){
        level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(), Block.UPDATE_CLIENTS);
    }
}
