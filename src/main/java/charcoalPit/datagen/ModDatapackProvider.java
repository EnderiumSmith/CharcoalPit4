package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.tree.ModTreeFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder builder=new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModTreeFeatures::bootstrap);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, builder, Set.of(CharcoalPit.MODID));
    }
}
