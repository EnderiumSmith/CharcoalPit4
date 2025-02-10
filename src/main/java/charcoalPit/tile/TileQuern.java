package charcoalPit.tile;

import charcoalPit.core.RecipeRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.recipe.QuernRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TileQuern extends BlockEntity {

    public ItemStackHandler inventory;
    public int time, total;
    Random random=new Random();

    public TileQuern(BlockPos pos, BlockState blockState) {
        super(TileEntityRegistry.QUERN.get(), pos, blockState);
        inventory=new ItemStackHandler(2){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(slot==0)
                    sendUpdate();
            }
        };
        time=0;
    }

    public void tick(){
        if(total>0){
            if(time<total) {
                time++;
                if(level.isClientSide && !inventory.getStackInSlot(0).isEmpty()){
                    Vec3 vec3 = new Vec3(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM,inventory.getStackInSlot(0)),getBlockPos().getX()+0.5F,getBlockPos().getY()+(13F/16F),getBlockPos().getZ()+0.5F,
                            vec3.x,vec3.y,vec3.z);
                }
            }
            if(time>=total){
                time=0;
                total=0;
                if(!level.isClientSide){
                    QuernRecipe recipe=getRecipeFor();
                    if(recipe!=null){
                        inventory.insertItem(1,recipe.getResultItem(level.registryAccess()).copy(),false);
                        inventory.extractItem(0,1,false);
                    }
                }
            }
        }
    }

    public boolean tryActivate(){
        if(total!=0)
            return false;
        QuernRecipe recipe=getRecipeFor();
        if(recipe!=null && inventory.insertItem(1,recipe.getResultItem(level.registryAccess()).copy(),true)==ItemStack.EMPTY){
            total=recipe.getCookingTime();
            sendUpdate();
            return true;
        }
        return false;
    }

    public QuernRecipe getRecipeFor(){
        return level.getRecipeManager().getRecipeFor(RecipeRegistry.QUERN_RECIPE.get(), new SingleRecipeInput(inventory.getStackInSlot(0)),level).map(RecipeHolder::value).orElse(null);
    }

    public void dropInventory(){
        if(!inventory.getStackInSlot(0).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(0));
        if(!inventory.getStackInSlot(1).isEmpty())
            Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), inventory.getStackInSlot(1));
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
            if(isItemValid(slot, stack))
                return inventory.insertItem(0,stack,simulate);
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return inventory.extractItem(1,amount,simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return true;
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",inventory.serializeNBT(registries));
        tag.putInt("time",time);
        tag.putInt("total",total);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
        time=tag.getInt("time");
        total=tag.getInt("total");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag=super.getUpdateTag(registries);
        saveAdditional(tag,registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        loadAdditional(tag,lookupProvider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    public void sendUpdate(){
        level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(), Block.UPDATE_CLIENTS);
    }
}
