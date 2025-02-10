package charcoalPit.tile;

import charcoalPit.core.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TileTheVat extends BlockEntity {

    public FluidTank vat,distillate,concentrate;
    public ItemStackHandler inventory;
    public int process,total,burnTime,burnTotal;

    //0,1,2,3=input,4=bottle,5=output,6=fuel;

    public TileTheVat(BlockPos pos, BlockState blockState) {
        super(null, pos, blockState);
        vat=new FluidTank(16000);
        distillate=new FluidTank(4000);
        concentrate=new FluidTank(4000);
        inventory=new ItemStackHandler(7);
        process=0;
        total=0;
        burnTime=0;
        burnTotal=0;

        vat.setFluid(new FluidStack(Fluids.WATER,16000));
        distillate.setFluid(new FluidStack(Fluids.WATER,4000));
        concentrate.setFluid(new FluidStack(Fluids.WATER,4000));
    }

    public void tick(){

    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("vat",vat.writeToNBT(registries,new CompoundTag()));
        tag.put("distillate",distillate.writeToNBT(registries,new CompoundTag()));
        tag.put("concentrate",concentrate.writeToNBT(registries,new CompoundTag()));
        tag.put("inventory",inventory.serializeNBT(registries));
        tag.putInt("process",process);
        tag.putInt("total",total);
        tag.putInt("burnTime",burnTime);
        tag.putInt("burnTotal",burnTotal);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        vat.readFromNBT(registries,tag.getCompound("vat"));
        distillate.readFromNBT(registries,tag.getCompound("distillate"));
        concentrate.readFromNBT(registries,tag.getCompound("concentrate"));
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
        process=tag.getInt("process");
        total=tag.getInt("total");
        burnTime=tag.getInt("burnTime");
        burnTotal=tag.getInt("burnTotal");
    }
}
