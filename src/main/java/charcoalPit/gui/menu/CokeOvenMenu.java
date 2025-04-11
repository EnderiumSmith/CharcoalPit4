package charcoalPit.gui.menu;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import charcoalPit.block.BlockCokeOven;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.recipe.CokingRecipe;
import charcoalPit.tile.TileCokeOven;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CokeOvenMenu extends AbstractContainerMenu {

	public ContainerData array;
	public ContainerLevelAccess world;
	public TileCokeOven tile;

	public CokeOvenMenu(int containerId, Inventory playerInv){
		this(containerId,playerInv,new ItemStackHandler(2){
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				if(slot==0){
					return CokingRecipe.getRecipeFor(stack, Minecraft.getInstance().level)!=null;
				}
				return true;
			}
		},new SimpleContainerData(3),ContainerLevelAccess.NULL,null);
	}

	public CokeOvenMenu(int containerId, Inventory playerInv, TileCokeOven tileCokeOven, ContainerLevelAccess world){
		this(containerId, playerInv, tileCokeOven.inventory, new ContainerData() {
			@Override
			public int get(int index) {
				if(index==0)
					return tileCokeOven.burnTime;
				if(index==1)
					return tileCokeOven.burnTotal;
				if(index==2)
					return tileCokeOven.creosote.getFluidAmount();
				return 0;
			}

			@Override
			public void set(int index, int value) {
				//no need to set on the server
			}

			@Override
			public int getCount() {
				return 3;
			}
		},world,tileCokeOven);
	}

	public CokeOvenMenu(int containerId, Inventory playerInv, ItemStackHandler machine, ContainerData array, ContainerLevelAccess world, TileCokeOven tileCokeOven) {
		super(MenuTypeRegistry.COKE_OVEN.get(), containerId);
		this.world=world;
		this.tile=tileCokeOven;
		this.addSlot(new SlotItemHandler(machine,0,71,44));
		this.addSlot(new SlotItemHandler(machine,1,98,53){
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		for(int k = 0; k < 3; ++k) {
			for(int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
			}
		}

		for(int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
		}

		this.array=array;
		this.addDataSlots(array);

	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 2) {
				if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean stillValid(Player player) {
		return world.evaluate(
				(level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockCokeOven && player.canInteractWithBlock(pos, 4.0), true
		);
	}

}
