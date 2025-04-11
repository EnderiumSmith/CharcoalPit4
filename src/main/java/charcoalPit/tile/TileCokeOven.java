package charcoalPit.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
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

import charcoalPit.block.BlockCokeOven;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.RecipeRegistry;
import charcoalPit.core.TileEntityRegistry;
import charcoalPit.fluid.FluidRegistry;
import charcoalPit.recipe.CokingRecipe;
import charcoalPit.recipe.QuernRecipe;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class TileCokeOven extends BlockEntity {

	public ItemStackHandler inventory;
	public FluidTank creosote;
	public int burnTime,burnTotal;
	public ItemStack previous;
	public boolean assembled;
	public TileCokeOven master;
	public boolean isMaster;

	public TileCokeOven(BlockPos pos, BlockState blockState) {
		super(TileEntityRegistry.COKE_OVEN.get(), pos, blockState);
		inventory=new ItemStackHandler(2){
			@Override
			public void setStackInSlot(int slot, ItemStack stack) {
				if(slot==0&&previous.isEmpty())
					previous=inventory.getStackInSlot(0).copy();
				super.setStackInSlot(slot, stack);
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				if(slot==0&&previous.isEmpty())
					previous=inventory.getStackInSlot(0).copy();
				return super.extractItem(slot, amount, simulate);
			}

			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				if(slot==0)
					return CokingRecipe.getRecipeFor(stack,level)!=null;
				return super.isItemValid(slot, stack);
			}
		};
		creosote=new FluidTank(16000);
		burnTime=0;
		burnTotal=0;
		previous=ItemStack.EMPTY;
		master=null;
		isMaster=false;
	}

	public void tick(){
		if(!assembled||(isMaster&&master==null)){
			tryAssemble();
		}
		if(assembled&&isMaster&&master!=null){
			if(burnTime>0&&!previous.isEmpty()){
				if(inventory.getStackInSlot(0).getItem()!=previous.getItem()){
					burnTime=-1;
					burnTotal=0;
					setActive(false);
				}
				previous=ItemStack.EMPTY;
			}
			if(burnTotal>0){
				if(burnTime>0)
					burnTime--;
				if(burnTime<=0){
					//done
					burnTotal=0;
					CokingRecipe recipe=getRecipeFor();
					if(recipe!=null){
						inventory.insertItem(1,recipe.getResult().copy(),false);
						inventory.extractItem(0,1,false);
						creosote.fill(new FluidStack(FluidRegistry.CREOSOTE.source, (int) recipe.getExperience()), IFluidHandler.FluidAction.EXECUTE);
						CokingRecipe recipe2=getRecipeFor();
						if(recipe2!=null){
							if(inventory.getStackInSlot(1).isEmpty()||
									(inventory.getStackInSlot(1).getItem()==recipe2.getResult().getItem()&&
											inventory.getStackInSlot(1).getCount()+recipe2.getResult().getCount()<=recipe2.getResult().getMaxStackSize())){
								burnTotal=recipe2.getCookingTime();
								burnTime=burnTotal;
							}else
								setActive(false);
						}else{
							setActive(false);
						}
					}
				}
			}else{
				CokingRecipe recipe2=getRecipeFor();
				if(recipe2!=null){
					if(inventory.getStackInSlot(1).isEmpty()||
							(inventory.getStackInSlot(1).getItem()==recipe2.getResult().getItem()&&
									inventory.getStackInSlot(1).getCount()+recipe2.getResult().getCount()<=recipe2.getResult().getMaxStackSize())){
						burnTotal=recipe2.getCookingTime();
						burnTime=burnTotal;
						setActive(true);
					}
				}
			}
		}
	}

	public void setActive(boolean active){
		if(getBlockState().getValue(BlockCokeOven.LIT)!=active){
			for(int y= worldPosition.getY();y<= worldPosition.getY()+1;y++)
				for(int x= worldPosition.getX();x<= worldPosition.getX()+1;x++)
					for(int z= worldPosition.getZ();z<= worldPosition.getZ()+1;z++){
						BlockPos pos=BlockPos.containing(x,y,z);
						if(level.getBlockState(pos).getBlock()==BlockRegistry.COKE_OVEN.get()) {
							level.setBlockAndUpdate(pos,level.getBlockState(pos).setValue(BlockCokeOven.LIT,active));
						}
					}
		}
	}

	public CokingRecipe getRecipeFor(){
		if(inventory.getStackInSlot(0).isEmpty())
			return null;
		return level.getRecipeManager().getRecipeFor(RecipeRegistry.COKING_RECIPE.get(), new SingleRecipeInput(inventory.getStackInSlot(0)),level).map(RecipeHolder::value).orElse(null);
	}

	public void tryAssemble(){
		for(int y= worldPosition.getY();y<= worldPosition.getY()+1;y++)
			for (int x = worldPosition.getX(); x <= worldPosition.getX() + 1; x++)
				for (int z = worldPosition.getZ(); z <= worldPosition.getZ() + 1; z++) {
					BlockPos pos = BlockPos.containing(x, y, z);
					if (level.getBlockState(pos).getBlock() != BlockRegistry.COKE_OVEN.get())
						return;
					TileCokeOven oven = (TileCokeOven) level.getBlockEntity(pos);
					if (oven.assembled) {
						if (oven.master != null || !isMaster)
							return;
					}
				}
		//multiblock valid
		boolean layer2=false;
		for(int y= worldPosition.getY();y<= worldPosition.getY()+1;y++) {
			int state=1;
			for (int z = worldPosition.getZ(); z <= worldPosition.getZ() + 1; z++)
				for (int x = worldPosition.getX(); x <= worldPosition.getX() + 1; x++) {
					BlockPos pos = BlockPos.containing(x, y, z);
					level.setBlockAndUpdate(pos,getBlockState().setValue(BlockCokeOven.STATE,layer2?5-state:state));
					state++;
					TileCokeOven oven = (TileCokeOven) level.getBlockEntity(pos);
					oven.assembled = true;
					oven.master = this;
					if (oven == this) {
						isMaster = true;
					}
					oven.sendUpdate();
				}
			layer2=true;
		}
	}

	public void disassemble(){
		if(isMaster) {
			dropInventory();
			isMaster=false;
		}
		for(int y= worldPosition.getY();y<= worldPosition.getY()+1;y++)
			for(int x= worldPosition.getX();x<= worldPosition.getX()+1;x++)
				for(int z= worldPosition.getZ();z<= worldPosition.getZ()+1;z++){
					BlockPos pos=BlockPos.containing(x,y,z);
					if(level.getBlockState(pos).getBlock()==BlockRegistry.COKE_OVEN.get()) {
						level.setBlockAndUpdate(pos,getBlockState().setValue(BlockCokeOven.LIT,false).setValue(BlockCokeOven.STATE,0));
						TileCokeOven oven = (TileCokeOven) level.getBlockEntity(pos);
						oven.assembled=false;
						oven.master=null;
						oven.sendUpdate();
					}
				}
	}

	public void dropInventory(){
		for(int i=0;i<2;i++){
			if(!inventory.getStackInSlot(i).isEmpty()){
				Containers.dropItemStack(level, getBlockPos().getX(), getBlockPos().getY(),getBlockPos().getZ(),inventory.getStackInSlot(i).copy());
				inventory.setStackInSlot(i,ItemStack.EMPTY);
			}
		}
	}

	public final IItemHandler out=new IItemHandler() {
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
			return inventory.isItemValid(slot,stack);
		}
	};

	public final IFluidHandler fluid=new IFluidHandler() {
		@Override
		public int getTanks() {
			return 1;
		}

		@Override
		public FluidStack getFluidInTank(int tank) {
			return creosote.getFluid();
		}

		@Override
		public int getTankCapacity(int tank) {
			return creosote.getCapacity();
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
			return creosote.drain(resource,action);
		}

		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			return creosote.drain(maxDrain,action);
		}
	};

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		tag.putBoolean("isMaster",isMaster);
		tag.put("inventory", inventory.serializeNBT(registries));
		tag.put("creosote", creosote.writeToNBT(registries, new CompoundTag()));
		tag.putInt("burnTime", burnTime);
		tag.putInt("burnTotal", burnTotal);
		tag.putBoolean("assembled", assembled);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		inventory.deserializeNBT(registries, tag.getCompound("inventory"));
		creosote.readFromNBT(registries, tag.getCompound("creosote"));
		burnTime = tag.getInt("burnTime");
		burnTotal = tag.getInt("burnTotal");
		assembled=tag.getBoolean("assembled");
		isMaster=tag.getBoolean("isMaster");
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
