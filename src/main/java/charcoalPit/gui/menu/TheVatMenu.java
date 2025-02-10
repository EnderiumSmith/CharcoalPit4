package charcoalPit.gui.menu;

import charcoalPit.block.BlockBarrel;
import charcoalPit.block.BlockTheVat;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.tile.TileBarrel;
import charcoalPit.tile.TileTheVat;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class TheVatMenu extends AbstractContainerMenu {


    public ContainerData array;
    public ContainerLevelAccess world;
    public TileTheVat machine;
    public ItemStackHandler fluidTag;
    public IFluidHandler tank1,tank2,tank3;

    public TheVatMenu(int containerId, Inventory playerInv){
        this(containerId,playerInv,new ItemStackHandler(7),null,new SimpleContainerData(4),ContainerLevelAccess.NULL);
    }

    public TheVatMenu(int containerId, Inventory playerInv, TileTheVat machine, ContainerLevelAccess world){
        this(containerId, playerInv, machine.inventory, machine, new ContainerData() {
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
                return 0;
            }

            @Override
            public void set(int i, int i1) {

            }

            @Override
            public int getCount() {
                return 4;
            }
        },world);
    }

    public TheVatMenu(int containerId, Inventory playerInv, ItemStackHandler inv, TileTheVat machine, ContainerData array, ContainerLevelAccess world){
        super(null, containerId);
        this.world=world;
        this.machine=machine;
        this.addSlot(new SlotItemHandler(inv,0,8,17));
        this.addSlot(new SlotItemHandler(inv,1,26,17));
        this.addSlot(new SlotItemHandler(inv,2,44,17));
        this.addSlot(new SlotItemHandler(inv,3,62,17));
        this.addSlot(new SlotItemHandler(inv,4,26,53));
        this.addSlot(new SlotItemHandler(inv,5,44,53));
        this.addSlot(new SlotItemHandler(inv,6,44,71));

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInv, i1 + k * 9 + 9, 8 + i1 * 18, 102 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInv, l, 8 + l * 18, 160));
        }

        fluidTag=new ItemStackHandler(3);
        fluidTag.setStackInSlot(0,new ItemStack(Items.PAPER));
        fluidTag.setStackInSlot(1,new ItemStack(Items.PAPER));
        fluidTag.setStackInSlot(2,new ItemStack(Items.PAPER));

        if(machine!=null) {
            this.tank1 = machine.vat;
            this.tank2 = machine.distillate;
            this.tank3 = machine.concentrate;
        }
        else {
            this.tank1 = new FluidTank(16000);
            this.tank2 = new FluidTank(4000);
            this.tank3 = new FluidTank(4000);
        }
        fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(tank1.getFluidInTank(0)));
        fluidTag.getStackInSlot(1).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(tank2.getFluidInTank(0)));
        fluidTag.getStackInSlot(2).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(tank3.getFluidInTank(0)));

        this.addSlot(new SlotItemHandler(fluidTag,0,0,0){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return false;
            }

            @Override
            public boolean isActive() {
                return false;
            }
        });
        this.addSlot(new SlotItemHandler(fluidTag,1,0,0){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return false;
            }

            @Override
            public boolean isActive() {
                return false;
            }
        });
        this.addSlot(new SlotItemHandler(fluidTag,2,0,0){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player playerIn) {
                return false;
            }

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
        if(fluidTag.getStackInSlot(0).get(DataComponentRegistry.FLUID_DATA).matches(tank1.getFluidInTank(0))){
            fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(tank1.getFluidInTank(0)));
        }
        if(fluidTag.getStackInSlot(1).get(DataComponentRegistry.FLUID_DATA).matches(tank2.getFluidInTank(0))){
            fluidTag.getStackInSlot(1).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(tank2.getFluidInTank(0)));
        }
        if(fluidTag.getStackInSlot(2).get(DataComponentRegistry.FLUID_DATA).matches(tank3.getFluidInTank(0))){
            fluidTag.getStackInSlot(2).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(tank3.getFluidInTank(0)));
        }
        super.broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 7) {
                if (!this.moveItemStackTo(itemstack1, 7, 42, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 7, false)) {
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockTheVat && player.canInteractWithBlock(pos, 4.0), true
        );
    }


}
