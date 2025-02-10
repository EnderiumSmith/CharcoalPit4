package charcoalPit.fluid;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class TankOnlyFluid {

    public final String name;
    public final ResourceLocation stillTexture;
    public final Supplier<FluidType> fluidType;
    public DeferredHolder<Fluid, BaseFlowingFluid> source;
    public DeferredItem<Item> bucket;

    public TankOnlyFluid(String fluidName, FluidType.Properties properties){
        this.name=fluidName;
        this.stillTexture=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/"+fluidName+"_still");
        this.fluidType=FluidRegistry.FLUID_TYPES.register(fluidName,()->new FluidType(properties));
        this.source=FluidRegistry.FLUIDS.register(fluidName,()->new BaseFlowingFluid.Source(new BaseFlowingFluid.Properties(fluidType,source,source).bucket(bucket)));
        this.bucket= ItemRegistry.ITEMS.register(fluidName+"_bucket",()->new BucketItem(source.get(),new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }

}
