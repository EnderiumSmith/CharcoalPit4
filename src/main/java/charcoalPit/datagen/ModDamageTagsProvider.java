package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModDamageTagsProvider extends DamageTypeTagsProvider {
    public ModDamageTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,@Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CharcoalPit.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.IS_PLAYER_ATTACK)
                .add(ModTags.MUSKET,ModTags.MUSKET_SILVER);
        tag(DamageTypeTags.IS_PROJECTILE)
                .add(ModTags.MUSKET,ModTags.MUSKET_SILVER);
        tag(DamageTypeTags.BYPASSES_EFFECTS)
                .add(ModTags.MUSKET_SILVER);


    }
}
