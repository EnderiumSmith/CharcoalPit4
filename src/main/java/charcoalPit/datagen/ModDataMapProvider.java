package charcoalPit.datagen;

import charcoalPit.core.ItemRegistry;
import charcoalPit.dataMap.DataMapRegistry;
import charcoalPit.dataMap.FuelTemperatureData;
import charcoalPit.fluid.FluidRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ItemRegistry.COKE.getId(),new FurnaceFuel(3200),false)
                .add(ItemRegistry.CHARCOAL_BLOCK.getId(),new FurnaceFuel(16000),false)
                .add(ItemRegistry.COKE_BLOCK.getId(),new FurnaceFuel(32000),false)
                .add(ItemRegistry.AETERNALIS.getId(),new FurnaceFuel(200),false)
                .add(FluidRegistry.CREOSOTE.bucket.getId(),new FurnaceFuel(4800),false)
                .add(FluidRegistry.ETHANOL.bucket.getId(),new FurnaceFuel(15200),false)
                .add(FluidRegistry.BIODIESEL.bucket.getId(),new FurnaceFuel(25600),false)
                .add(ItemRegistry.GLYCERINE.getId(),new FurnaceFuel(1200),false)
                .add(ItemRegistry.ETHANOL_BOTTLE.getId(),new FurnaceFuel(3800),false)
                .add(ItemRegistry.COKE_POWDER.getId(),new FurnaceFuel(3200),false)
                .add(FluidRegistry.ACETYLENE.bucket.getId(),new FurnaceFuel(400),false)
                .add(ItemRegistry.BAMBOO_CHARCOAL.getId(),new FurnaceFuel(200*4), false);
        this.builder(DataMapRegistry.FUEL_TEMPERATURE)
                .add(ItemTags.LOGS_THAT_BURN,new FuelTemperatureData(700, false),false)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(Items.CHARCOAL),new FuelTemperatureData(900,true),false)
                .add(ItemRegistry.CHARCOAL_BLOCK,new FuelTemperatureData(900,true),false)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(Items.COAL),new FuelTemperatureData(1000,false),false)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(Items.COAL_BLOCK),new FuelTemperatureData(1000,false),false)
                .add(ItemRegistry.COKE,new FuelTemperatureData(1200,true),false)
                .add(ItemRegistry.COKE_BLOCK,new FuelTemperatureData(1200,true),false)
                .add(BuiltInRegistries.ITEM.wrapAsHolder(Items.BLAZE_ROD),new FuelTemperatureData(1600,true),false)
                .add(ItemRegistry.AETERNALIS,new FuelTemperatureData(1100,true),false);
        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ItemRegistry.CHERRY.getId(), new Compostable(0.65F),false)
                .add(ItemRegistry.WALNUT.getId(), new Compostable(0.65F),false)
                .add(ItemRegistry.OLIVES.getId(), new Compostable(0.65F),false)
                .add(ItemRegistry.ORANGE.getId(), new Compostable(0.65F),false)
                .add(ItemRegistry.LEEKS.getId(), new Compostable(0.65F),false)
                .add(ItemRegistry.AMARANTH_SAPLING.getId(), new Compostable(1F),false)
                .add(ItemRegistry.APPLE_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.ORANGE_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.WALNUT_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.OLIVE_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.CHERRY_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.DOUGLAS_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.AMARANTH_LEAVES.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.APPLE_SAPLING.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.ORANGE_SAPLING.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.OLIVE_SAPLING.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.WALNUT_SAPLING.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.DOUGLAS_SAPLING.getId(), new Compostable(0.3F),false)
                .add(ItemRegistry.CHERRY_SAPLING.getId(), new Compostable(0.3F),false);
    }
}
