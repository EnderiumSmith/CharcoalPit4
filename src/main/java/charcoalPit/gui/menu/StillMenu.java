package charcoalPit.gui.menu;

import charcoalPit.block.BlockStill;
import charcoalPit.core.DataComponentRegistry;
import charcoalPit.gui.MenuTypeRegistry;
import charcoalPit.recipe.StillRecipe;
import charcoalPit.tile.TIleStill;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class StillMenu extends AbstractContainerMenu {

    public ContainerData array;
    public ContainerLevelAccess world;
    public TIleStill machine;
    public ItemStackHandler fluidTag;
    public IFluidHandler input,output;

    public StillMenu(int containerId, Inventory playerInv) {
        this(containerId,playerInv,new ItemStackHandler(4),null,new SimpleContainerData(4),ContainerLevelAccess.NULL);
    }

    public StillMenu(int containerId, Inventory playerInv, TIleStill machine, ContainerLevelAccess world) {
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
                return 0;
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public void set(int i, int i1) {

            }
        },world);
    }

    public StillMenu(int containerId, Inventory playerInv, ItemStackHandler inv, TIleStill machine, ContainerData array, ContainerLevelAccess world) {
        super(MenuTypeRegistry.STILL.get(), containerId);
        this.world=world;
        this.machine=machine;
        this.addSlot(new SlotItemHandler(inv,0,53,17){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return StillRecipe.isValidIngredient(stack, Minecraft.getInstance().level);
            }
        });
        this.addSlot(new SlotItemHandler(inv,3,53,35){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return StillRecipe.isValidCatalyst(stack, Minecraft.getInstance().level);
            }
        });
        this.addSlot(new SlotItemHandler(inv,2,53,53){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getBurnTime(null)>0;
            }
        });
        this.addSlot(new SlotItemHandler(inv,1,107,53){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                if(machine!=null){
                    if(machine.xp>0){
                        player.giveExperiencePoints((int) machine.xp);
                        machine.xp-=(int)machine.xp;
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

        fluidTag=new ItemStackHandler(2);
        fluidTag.setStackInSlot(0,new ItemStack(Items.PAPER));
        fluidTag.setStackInSlot(1,new ItemStack(Items.PAPER));
        if(machine!=null) {
            this.input = machine.input;
            this.output= machine.output;
        }
        else {
            this.input = new FluidTank(16000);
            this.output= new FluidTank(16000);
        }
        fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(input.getFluidInTank(0)));
        fluidTag.getStackInSlot(1).set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(output.getFluidInTank(0)));

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
        this.addSlot(new SlotItemHandler(fluidTag,1,0,0){
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
        if(!fluidTag.getStackInSlot(0).get(DataComponentRegistry.FLUID_DATA).matches(input.getFluidInTank(0))){
            fluidTag.getStackInSlot(0).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(input.getFluidInTank(0)));
        }
        if(!fluidTag.getStackInSlot(1).get(DataComponentRegistry.FLUID_DATA).matches(output.getFluidInTank(0))){
            fluidTag.getStackInSlot(1).set(DataComponentRegistry.FLUID_DATA,SimpleFluidContent.copyOf(output.getFluidInTank(0)));
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
            if (index < 4) {
                if (!this.moveItemStackTo(itemstack1, 3, 40, true)) {
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
                (level, pos) -> level.getBlockState(pos).getBlock() instanceof BlockStill && player.canInteractWithBlock(pos, 4.0), true
        );
    }
}
