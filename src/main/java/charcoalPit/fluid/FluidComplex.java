package charcoalPit.fluid;

import charcoalPit.CharcoalPit;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import java.util.function.Supplier;

public class FluidComplex {

    public final String name;
    public final ResourceLocation stillTexture;
    public final ResourceLocation flowingTexture;
    public final Supplier<FluidType> fluidType;
    public DeferredHolder<Fluid,BaseFlowingFluid> source;
    public DeferredHolder<Fluid,BaseFlowingFluid> flowing;
    public DeferredBlock<LiquidBlock> fluidBlock;
    public DeferredItem<Item> bucket;

    public FluidComplex(String fluidName, FluidType.Properties properties){
        this.name=fluidName;
        this.stillTexture=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/"+fluidName+"_still");
        this.flowingTexture=ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"block/"+fluidName+"_flow");
        this.fluidType=FluidRegistry.FLUID_TYPES.register(fluidName,()->new FluidType(properties));
        this.source=FluidRegistry.FLUIDS.register(fluidName,()->new BaseFlowingFluid.Source(new BaseFlowingFluid.Properties(fluidType,source,flowing).block(fluidBlock).bucket(bucket)));
        this.flowing=FluidRegistry.FLUIDS.register(fluidName+"flow",()->new BaseFlowingFluid.Flowing(new BaseFlowingFluid.Properties(fluidType,source,flowing).block(fluidBlock).bucket(bucket)));
        this.fluidBlock=BlockRegistry.BLOCKS.register(fluidName,()->new BlockCreosote(source.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));
        this.bucket=ItemRegistry.ITEMS.register(fluidName+"_bucket",()->new BucketItem(source.get(),new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }




}
