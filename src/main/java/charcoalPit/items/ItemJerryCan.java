package charcoalPit.items;

import charcoalPit.core.DataComponentRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemJerryCan extends Item {
    public ItemJerryCan(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.has(DataComponentRegistry.FLUID_DATA);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        if(stack.has(DataComponentRegistry.FLUID_DATA)){
            SimpleFluidContent fluidContent=stack.get(DataComponentRegistry.FLUID_DATA);
            return Math.round((float)fluidContent.getAmount()*13.0F / 4000F);
        }
        return 0;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        if(stack.has(DataComponentRegistry.FLUID_DATA)){
            SimpleFluidContent fluidContent=stack.get(DataComponentRegistry.FLUID_DATA);
            float f=Math.max(0.0F, (float)fluidContent.getAmount()/4000F);
            return Mth.hsvToRgb(f/3.0F,1F,1F);
        }
        return Mth.hsvToRgb(0F,1F,1F);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.has(DataComponentRegistry.FLUID_DATA);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack stack=new ItemStack(this);
        if(itemStack.has(DataComponentRegistry.FLUID_DATA)){
            FluidStack fluidStack=itemStack.get(DataComponentRegistry.FLUID_DATA).copy();
            fluidStack.setAmount(fluidStack.getAmount()-100);
            if(!fluidStack.isEmpty()){
                stack.set(DataComponentRegistry.FLUID_DATA, SimpleFluidContent.copyOf(fluidStack));
            }
        }
        return stack;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        if(itemStack.has(DataComponentRegistry.FLUID_DATA)){
            SimpleFluidContent fluidContent=itemStack.get(DataComponentRegistry.FLUID_DATA);
            if(!fluidContent.isEmpty() && fluidContent.getAmount()>=100) {
                return ItemJerryCan.getFuelBurnTime(fluidContent.getFluid());
            }
        }
        return 0;
    }

    public static int getFuelBurnTime(Fluid fluid){
        Item bucketItem = fluid.getBucket();
        if (bucketItem != null) {
            FurnaceFuel fuel = bucketItem.builtInRegistryHolder().getData(NeoForgeDataMaps.FURNACE_FUELS);
            if (fuel != null) {
                return fuel.burnTime() / 10;
            }
        }
        return 0;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if(stack.has( DataComponentRegistry.FLUID_DATA)){
            return stack.get(DataComponentRegistry.FLUID_DATA).isEmpty()?super.getMaxStackSize(stack):1;
        }
        return super.getMaxStackSize(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.has(DataComponentRegistry.FLUID_DATA)){
            SimpleFluidContent fluid=stack.get(DataComponentRegistry.FLUID_DATA);
            FluidStack fluidStack=fluid.copy();
            tooltipComponents.add(fluidStack.getHoverName().plainCopy().append(Component.literal(":"+fluidStack.getAmount())).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
