package charcoalPit.gui.menu;

import charcoalPit.block.BlockPress;
import charcoalPit.block.BlockStill;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.tile.TIleStill;
import charcoalPit.tile.TilePress;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class PressMenu extends AbstractContainerMenu {

    public ContainerData array;
    public ContainerLevelAccess world;
    public TilePress machine;
    public ItemStackHandler fluidTag;
    public IFluidHandler output;

    public PressMenu(int containerId, Inventory playerInv) {
        this(containerId,playerInv,new ItemStackHandler(2),null,new SimpleContainerData(5),ContainerLevelAccess.NULL);
    }

    public PressMenu(int containerId, Inventory playerInv, TilePress machine, ContainerLevelAccess world) {
        this(containerId,playerInv,machine.inventory,machine,new ContainerData(){
            @Override
            public int get(int i) {
                if(i==0)
                    return machine.process;
                if(i==1)
                    return machine.total;
                if(i==2)
                    return machine.burnTime;
                if(i==3)
                    return machine.burnTotal;
                if(i==4)
                    return machine.input.getFluidAmount();
                return 0;
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public void set(int i, int i1) {

            }
        },world);
    }

    public PressMenu(int containerId, Inventory playerInv, ItemStackHandler inv, TilePress machine, ContainerData array, ContainerLevelAccess world) {
        super(MenuTypeRegistry.PRESS.get(), containerId);
        this.world=world;
        this.machine=machine;
        this.addSlot(new SlotItemHandler(inv,0,65,35));
        this.addSlot(new SlotItemHandler(inv,1,91,53){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getBurnTime(null)>0;
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

        fluidTag=new ItemStackHandler(1);
        fluidTag.setStackInSlot(0,new ItemStack(Items.PAPER));
        if(machine!=null) {
            this.output= machine.output;
        }
        else {
            this.output= new FluidTank(16000);
        }
        fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(output.getFluidInTank(0)));

        this.addSlot(new SlotItemHandler(fluidTag,0,0,0){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return false;
            }

            @OnlyIn(Dist.CLIENT)
            @Override
            public boolean isActive() {
                return false;
            }
        });

        this.array=array;
        this.addDataSlots(array);
    }

    @Override
    public void broadcastChanges() {
        if(!fluidTag.getStackInSlot(0).get(DataComponentRegistry.FLUID_DATA).matches(output.getFluidInTank(0))){
            fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(output.getFluidInTank(0)));
        }
        super.broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        final int SLOTS=2;
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockPress && player.canInteractWithBlock(pos, 4.0), true
        );
    }
}
