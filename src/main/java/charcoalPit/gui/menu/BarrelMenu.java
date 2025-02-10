package charcoalPit.gui.menu;

import charcoalPit.block.BlockBarrel;
import charcoalPit.block.BlockBellows;
import charcoalPit.block.BlockBlastFurnace;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.tile.TileBarrel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BarrelMenu extends AbstractContainerMenu {

    public ContainerData array;
    public ContainerLevelAccess world;
    public TileBarrel machine;
    public ItemStackHandler fluidTag;
    public IFluidHandler tank;


    public BarrelMenu(int containerId, Inventory playerInv) {
        this(containerId,playerInv,new ItemStackHandler(2),null,new SimpleContainerData(2),ContainerLevelAccess.NULL);
    }
    public BarrelMenu(int containerId, Inventory playerInv, TileBarrel machine, ContainerLevelAccess world) {
        this(containerId, playerInv, new ItemStackHandler(2), machine, new ContainerData() {
            @Override
            public int get(int index) {
                return index==0?machine.process:machine.total;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int getCount() {
                return 2;
            }
        }, world);
    }
    public BarrelMenu(int containerId, Inventory playerInv, ItemStackHandler inv, TileBarrel machine, ContainerData array, ContainerLevelAccess world) {
        super(MenuTypeRegistry.BARREL.get(), containerId);
        this.world=world;
        this.machine=machine;
        this.addSlot(new SlotItemHandler(machine!=null?machine.input:inv, 0,98,17));
        this.addSlot(new SlotItemHandler(machine!=null?machine.output:inv,machine!=null?0:1,98,53){
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

        fluidTag=new ItemStackHandler(1);
        fluidTag.setStackInSlot(0,new ItemStack(Items.PAPER));
        if(machine!=null)
            this.tank=machine.tank;
        else
            this.tank=new FluidTank(16000);
        fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(tank.getFluidInTank(0)));

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
        if(!fluidTag.getStackInSlot(0).get(DataComponentRegistry.FLUID_DATA).matches(tank.getFluidInTank(0))){
            fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(tank.getFluidInTank(0)));
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
            if (index < 2) {
                if (!this.moveItemStackTo(itemstack1, 2, 37, true)) {
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockBarrel && player.canInteractWithBlock(pos, 4.0), true
        );
    }
}
