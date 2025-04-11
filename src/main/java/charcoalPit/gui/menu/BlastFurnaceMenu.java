package charcoalPit.gui.menu;

import charcoalPit.block.BlockBlastFurnace;
import charcoalPit.dataMap.DataMapRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.recipe.BlastFurnaceRecipe;
import charcoalPit.tile.TileBlastFurnace;
import charcoalPit.tile.TileBloomery;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BlastFurnaceMenu extends AbstractContainerMenu {

    public ContainerData array;
    public ContainerLevelAccess world;
    public TileBlastFurnace tile;

    public BlastFurnaceMenu(int containerId, Inventory playerInv){
        this(containerId,playerInv,new ItemStackHandler(4){
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if(slot==0||slot==1){
                    return BlastFurnaceRecipe.isIngredientValid(stack,slot==1, Minecraft.getInstance().level)||BlastFurnaceRecipe.isVanillaValid(stack,slot==1,Minecraft.getInstance().level);
                }
                if(slot==2){
                    Holder<Item> holder=stack.getItemHolder();
                    return holder.getData(DataMapRegistry.FUEL_TEMPERATURE)!=null&&holder.getData(DataMapRegistry.FUEL_TEMPERATURE).clean();
                }
                return true;
            }
        },new SimpleContainerData(5),ContainerLevelAccess.NULL,null);
    }

    public BlastFurnaceMenu(int containerId, Inventory playerInv, TileBlastFurnace tileBloomery, ContainerLevelAccess world){
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
        },world,tileBloomery);
    }

    public BlastFurnaceMenu(int containerId, Inventory playerInv, ItemStackHandler machine, ContainerData array, ContainerLevelAccess world, TileBlastFurnace tileBlast) {
        super(MenuTypeRegistry.BLAST_FURNACE.get(), containerId);
        this.world=world;
        this.tile=tileBlast;
        this.addSlot(new SlotItemHandler(machine,0,65,17));
        this.addSlot(new SlotItemHandler(machine,1,47,17));
        this.addSlot(new SlotItemHandler(machine,2,56,53));
        this.addSlot(new SlotItemHandler(machine,3,116,35){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                if(tile!=null){
                    if(tile.xp>0){
                        player.giveExperiencePoints((int) tile.xp);
                        tile.xp-=(int)tile.xp;
                    }
                }
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
            if (index < 4) {
                if (!this.moveItemStackTo(itemstack1, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockBlastFurnace && player.canInteractWithBlock(pos, 4.0), true
        );
    }

}
