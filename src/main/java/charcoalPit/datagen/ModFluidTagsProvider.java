package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ModTags;
import charcoalPit.fluid.FluidRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;

import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CharcoalPit.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.CREOSOTE_OIL)
                .add(FluidRegistry.CREOSOTE.source.get());
        tag(ModTags.BIODIESEL)
                .add(FluidRegistry.BIODIESEL.source.get());
        tag(ModTags.SEED_OIL)
                .add(FluidRegistry.SEED_OIL.source.get());
        tag(ModTags.ETHANOL)
                .add(FluidRegistry.ETHANOL.source.get());
        tag(ModTags.SULFURIC_ACID)
                .add(FluidRegistry.OIL_OF_VITRIOL.source.get());
        tag(ModTags.CHLORINE)
                .add(FluidRegistry.CHLORINE.source.get());
        tag(Tags.Fluids.GASEOUS)
                .add(FluidRegistry.HYDROGEN_SULFIDE.source.get(),FluidRegistry.MURIATIC_ACID.source.get(),FluidRegistry.CHLORINE.source.get(),FluidRegistry.ACETYLENE.source.get());
    }
}
