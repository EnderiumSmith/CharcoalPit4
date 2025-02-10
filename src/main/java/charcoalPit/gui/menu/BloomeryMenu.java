package charcoalPit.gui.menu;

import charcoalPit.block.BlockBloomery;
import charcoalPit.dataMap.DataMapRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.recipe.BloomeryRecipe;
import charcoalPit.tile.TileBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BloomeryMenu extends AbstractContainerMenu {

    public ContainerData array;
    public ContainerLevelAccess world;

    public BloomeryMenu(int containerId, Inventory playerInv){
        this(containerId,playerInv,new ItemStackHandler(3){
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if(slot==0){
                    return BloomeryRecipe.getRecipe(stack,Minecraft.getInstance().level)!=null;
                }
                if(slot==1){
                    Holder<Item> holder=stack.getItemHolder();
                    return holder.getData(DataMapRegistry.FUEL_TEMPERATURE)!=null;
                }
                return true;
            }
        },new SimpleContainerData(5),ContainerLevelAccess.NULL);
    }

    public BloomeryMenu(int containerId, Inventory playerInv, TileBloomery tileBloomery, ContainerLevelAccess world){
        this(containerId, playerInv, tileBloomery.inventory, new ContainerData() {
            @Override
            public int get(int index) {
                if(index==0)
                    return tileBloomery.progress;
                if(index==1)
                    return tileBloomery.processTotal;
                if(index==2)
                    return tileBloomery.burnTime;
                if(index==3)
                    return tileBloomery.burnTotal;
                if(index==4)
                    return tileBloomery.temperature;
                return 0;
            }

            @Override
            public void set(int index, int value) {
                //no need to set on the server
            }

            @Override
            public int getCount() {
                return 5;
            }
        },world);
    }

    public BloomeryMenu(int containerId, Inventory playerInv, ItemStackHandler machine, ContainerData array, ContainerLevelAccess world) {
        super(MenuTypeRegistry.BLOOMERY.get(), containerId);
        this.world=world;
        this.addSlot(new SlotItemHandler(machine,0,56,17));
        this.addSlot(new SlotItemHandler(machine,1,56,53));
        this.addSlot(new SlotItemHandler(machine,2,116,35){
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
            if (index < 3) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockBloomery && player.canInteractWithBlock(pos, 4.0), true
        );
    }
}
