package charcoalPit.dataMap;

import charcoalPit.CharcoalPit;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = CharcoalPit.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataMapRegistry {

    public static final DataMapType<Item, FuelTemperatureData> FUEL_TEMPERATURE=DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"fuel_temperature"),
            Registries.ITEM,
            FuelTemperatureData.CODEC
    ).synced(FuelTemperatureData.CODEC,false).build();

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event){
        event.register(FUEL_TEMPERATURE);
    }

}
