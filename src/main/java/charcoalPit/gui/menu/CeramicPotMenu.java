package charcoalPit.gui.menu;

import charcoalPit.block.BlockCeramicPot;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.tile.TIleCeramicPot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CeramicPotMenu extends AbstractContainerMenu {

    public ContainerLevelAccess world;

    public CeramicPotMenu(int containerId, Inventory playerInv){
        this(containerId,playerInv,new ItemStackHandler(9), ContainerLevelAccess.NULL);
    }

    public CeramicPotMenu(int containerId, Inventory playerInv, TIleCeramicPot tile, ContainerLevelAccess world){
        this(containerId,playerInv,tile.inventory,world);
    }

    public CeramicPotMenu(int containerId, Inventory playerInv, ItemStackHandler machine, ContainerLevelAccess world) {
        super(MenuTypeRegistry.CERAMIC_POT.get(), containerId);
        this.world=world;

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new SlotItemHandler(machine, j + i * 3, 62 + j * 18, 17 + i * 18){
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return stack.getItem().canFitInsideContainerItems();
                    }
                });
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 142));
        }

    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        final int SLOTS=9;
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < SLOTS) {
                if (!this.moveItemStackTo(itemstack1, SLOTS, 35+SLOTS, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, SLOTS, false)) {
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockCeramicPot && player.canInteractWithBlock(pos, 4.0), true
        );
    }
}
