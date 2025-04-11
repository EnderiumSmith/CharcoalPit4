package charcoalPit.core;

import charcoalPit.CharcoalPit;
import charcoalPit.items.ItemJerryCan;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.checkerframework.checker.units.qual.C;

@EventBusSubscriber(modid = CharcoalPit.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.BLOOMERY.get(), (tile, side)->tile.out);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.BLAST_FURNACE.get(), (tile,side)->tile.out);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.BARREL.get(), (tile,side)->tile.out);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.BARREL.get(), (tile,side)->tile.tank);
        event.registerItem(Capabilities.FluidHandler.ITEM,(stack,context)->new FluidHandlerItemStack(DataComponentRegistry.FLUID_DATA,stack,16000),ItemRegistry.BARREL.get());
        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level, pos, state, blockEntity, context) -> level.getCapability(Capabilities.ItemHandler.BLOCK,pos.below(),context),BlockRegistry.BLAST_FURNACE_MIDDLE.get());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.STILL.get(),(tile,side)->tile.fluids);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.STILL.get(),(tile,side)->(side==null||side.getAxis()==Direction.Axis.Y)?tile.items:tile.side_items);
        /*event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.CRUSHER.get(), (tile,side)->tile.fluids);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.CRUSHER.get(), (tile,side)->tile.items);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,TileEntityRegistry.CRUSHER.get(), (tile,side)->tile.battery);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.GENERATOR.get(), (tile,side)-> tile.fuel);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK,TileEntityRegistry.GENERATOR.get(), (tile,side)->(side!=null&&side.getAxis()==tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis())?tile.buffer:null);*/
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.PRESS.get(), (tile,side)->tile.fluids);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.PRESS.get(), (tile,side)->tile.items);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.QUERN.get(), (tile,side)-> tile.items);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.CERAMIC_POT.get(), (tile,side)->tile.inventory);
        event.registerItem(Capabilities.FluidHandler.ITEM,(stack,context)->new FluidHandlerItemStack(DataComponentRegistry.FLUID_DATA,stack,4000){
            @Override
            public boolean isFluidValid(int tank, FluidStack stack) {
                return ItemJerryCan.getFuelBurnTime(stack.getFluid())>0;
            }

            @Override
            public boolean canFillFluidType(FluidStack fluid) {
                return ItemJerryCan.getFuelBurnTime(fluid.getFluid())>0;
            }
        },ItemRegistry.JERRY_CAN.get());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.NESTING_BOX.get(), (tile,side)->tile.inventory);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.FEEDING_THROUGH.get(), (tile,side)->tile.inventory);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.BLAST_FURNACE.get(), (tile,side)->tile.gasBoost);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,TileEntityRegistry.COKE_OVEN.get(), (tile,side)->tile.master==null?null:tile.master.out);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,TileEntityRegistry.COKE_OVEN.get(), (tile,side)->tile.master==null?null:tile.master.fluid);
    }
}
