package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ItemRegistry;
import charcoalPit.core.ModTags;
import charcoalPit.fluid.FluidRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CharcoalPit.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.CHARCOAL_BLOCK.get(),BlockRegistry.COKE_BLOCK.get(),BlockRegistry.ACTIVE_COAL_PILE.get(),BlockRegistry.BLOOMERY_BRICK.get(),BlockRegistry.BLOOMERY_SANDY.get(),BlockRegistry.BLOOMERY_NETHER.get(),
                        BlockRegistry.CHIMNEY_BRICK.get(),BlockRegistry.CHIMNEY_SANDY.get(),BlockRegistry.BLOOM.get(),BlockRegistry.SANDY_BRICKS.get(),BlockRegistry.SANDY_STAIRS.get(),
                        BlockRegistry.SANDY_SLAB.get(),BlockRegistry.SANDY_WALL.get(),BlockRegistry.ORE_TIN.get(),BlockRegistry.MECHANICAL_BELLOWS.get(),BlockRegistry.BLAST_FURNACE.get(),
                        BlockRegistry.BASALT.get(),BlockRegistry.BASALT_POLISHED.get(),BlockRegistry.BASALT_BRICKS.get(),BlockRegistry.BASALT_WALL.get(),BlockRegistry.BASALT_SLAB.get(),
                        BlockRegistry.BASALT_PILLAR.get(),BlockRegistry.BASALT_STAIRS.get(),
                        BlockRegistry.MARBLE.get(),BlockRegistry.MARBLE_POLISHED.get(),BlockRegistry.MARBLE_BRICKS.get(),BlockRegistry.MARBLE_STAIRS.get(),BlockRegistry.MARBLE_SLAB.get(),
                        BlockRegistry.MARBLE_WALL.get(),BlockRegistry.MARBLE_PILLAR.get(),
                        BlockRegistry.HIGH_REFRACTORY_BRICKS.get(),BlockRegistry.BLAST_FURNACE_MIDDLE.get(),BlockRegistry.BLAST_FURNACE_CHIMNEY.get(),
                        BlockRegistry.BRONZE_BLOCK.get(),BlockRegistry.STILL.get(),BlockRegistry.STEEL_BLOCK.get(),BlockRegistry.PRESS.get(),BlockRegistry.TIN_BLOCK.get(),
                        BlockRegistry.QUERN.get(),BlockRegistry.CERAMIC_POT.get(),BlockRegistry.WHITE_POT.get(),BlockRegistry.LIGHT_GRAY_POT.get(),BlockRegistry.GRAY_POT.get(),BlockRegistry.BLACK_POT.get(),
                        BlockRegistry.BROWN_POT.get(),BlockRegistry.RED_POT.get(),BlockRegistry.ORANGE_POT.get(),BlockRegistry.YELLOW_POT.get(),BlockRegistry.LIME_POT.get(),
                        BlockRegistry.GREEN_POT.get(),BlockRegistry.CYAN_POT.get(),BlockRegistry.LIGHT_BLUE_POT.get(),BlockRegistry.BLUE_POT.get(),BlockRegistry.PURPLE_POT.get(),
                        BlockRegistry.MAGENTA_POT.get(),BlockRegistry.PINK_POT.get(),BlockRegistry.BELLOW_PUMP.get(),BlockRegistry.ALUMINIUM_BLOCK.get(),BlockRegistry.ALUMITE_BLOCK.get(),
                        BlockRegistry.ORE_PLATINUM.get(),BlockRegistry.ORE_DEEPSLATE_PLATINUM.get(),BlockRegistry.PLATINUM_BLOCK.get(),BlockRegistry.ENDERIUM_BLOCK.get(),
                        BlockRegistry.ORE_CHALCOCITE.get(),BlockRegistry.BAMBOO_CHARCOAL.get(),BlockRegistry.COKE_OVEN.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(BlockRegistry.WOOD_ASH.get(),BlockRegistry.COAL_ASH.get(),BlockRegistry.ASH_BLOCK.get(),BlockRegistry.GRAVEL_TIN.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(BlockRegistry.LOG_PILE.get(),BlockRegistry.ACTIVE_LOG_PILE.get(),BlockRegistry.BELLOWS.get(),BlockRegistry.BARREL.get(),BlockRegistry.NEST_BOX.get(),BlockRegistry.FEEDING_THROUGH.get());
        tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .add(BlockRegistry.BRONZE_BLOCK.get(),BlockRegistry.STEEL_BLOCK.get(),BlockRegistry.ALUMITE_BLOCK.get(),BlockRegistry.ORE_PLATINUM.get(),BlockRegistry.ORE_DEEPSLATE_PLATINUM.get(),BlockRegistry.PLATINUM_BLOCK.get());
        tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .add(BlockRegistry.ORE_TIN.get(),BlockRegistry.TIN_BLOCK.get(),BlockRegistry.ALUMINIUM_BLOCK.get(),BlockRegistry.ORE_CHALCOCITE.get());
        tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .add(BlockRegistry.ENDERIUM_BLOCK.get());
        tag(ModTags.REFRACTORY_BLOCKS)
                .add(Blocks.BRICKS,Blocks.BRICK_SLAB,Blocks.BRICK_STAIRS,
                        BlockRegistry.SANDY_BRICKS.get(),BlockRegistry.SANDY_STAIRS.get(),BlockRegistry.SANDY_SLAB.get(),
                        Blocks.NETHER_BRICKS,Blocks.NETHER_BRICK_SLAB,Blocks.NETHER_BRICK_STAIRS,Blocks.CRACKED_NETHER_BRICKS,Blocks.CHISELED_NETHER_BRICKS,
                        Blocks.RED_NETHER_BRICKS,Blocks.RED_NETHER_BRICK_SLAB,Blocks.RED_NETHER_BRICK_STAIRS,
                        Blocks.TINTED_GLASS,
                        BlockRegistry.ACTIVE_COAL_PILE.get(),BlockRegistry.COAL_ASH.get(),Blocks.COAL_BLOCK,
                        BlockRegistry.HIGH_REFRACTORY_BRICKS.get());
        tag(BlockTags.WALLS)
                .add(BlockRegistry.SANDY_WALL.get(),BlockRegistry.BASALT_WALL.get(),BlockRegistry.MARBLE_WALL.get());
        tag(ModTags.TIN_ORE)
                .add(BlockRegistry.ORE_TIN.get());
        tag(ModTags.PLATINUM_ORE)
                .add(BlockRegistry.ORE_PLATINUM.get(),BlockRegistry.ORE_DEEPSLATE_PLATINUM.get());
        tag(Tags.Blocks.ORES_COPPER)
                .add(BlockRegistry.ORE_CHALCOCITE.get());
        tag(Tags.Blocks.STORAGE_BLOCKS_RAW_COPPER)
                .add(BlockRegistry.RAW_CHALCOCITE_BLOCK.get());
        tag(Tags.Blocks.ORES)
                .addTag(ModTags.TIN_ORE).addTag(ModTags.PLATINUM_ORE);
        tag(BlockTags.CROPS)
                .add(BlockRegistry.LEEKS.get(),BlockRegistry.SUNFLOWER.get());
        tag(BlockTags.LEAVES)
                .add(BlockRegistry.APPLE_LEAVES.get(),BlockRegistry.CHERRY_LEAVES.get(),BlockRegistry.OLIVE_LEAVES.get(),BlockRegistry.ORANGE_LEAVES.get(),BlockRegistry.WALNUT_LEAVES.get(),
                        BlockRegistry.DOUGLAS_LEAVES.get(),BlockRegistry.AMARANTH_LEAVES.get());
        tag(ModTags.BRONZE_BLOCK_BLOCK).add(BlockRegistry.BRONZE_BLOCK.get());
        tag(ModTags.TIN_BLOCK_BLOCK).add(BlockRegistry.TIN_BLOCK.get());
        tag(ModTags.STEEL_BLOCK_BLOCK).add(BlockRegistry.STEEL_BLOCK.get());
        tag(ModTags.CHARCOAL_BLOCK_BLOCK).add(BlockRegistry.CHARCOAL_BLOCK.get());
        tag(ModTags.COKE_BLOCK_BLOCK).add(BlockRegistry.COKE_BLOCK.get());
        tag(ModTags.ALUMINUM_BLOCK_BLOCK).add(BlockRegistry.ALUMINIUM_BLOCK.get());
        tag(ModTags.ALUMITE_BLOCK_BLOCK).add(BlockRegistry.ALUMITE_BLOCK.get());
        tag(ModTags.PLATINUM_BLOCK_BLOCK).add(BlockRegistry.PLATINUM_BLOCK.get());
        tag(ModTags.ENDERIUM_BLOCK_BLOCK).add(BlockRegistry.ENDERIUM_BLOCK.get());

        tag(BlockTags.BEACON_BASE_BLOCKS).add(BlockRegistry.BRONZE_BLOCK.get(),BlockRegistry.STEEL_BLOCK.get(),BlockRegistry.ALUMINIUM_BLOCK.get(),BlockRegistry.ALUMITE_BLOCK.get(),BlockRegistry.PLATINUM_BLOCK.get(),BlockRegistry.ENDERIUM_BLOCK.get());
    }
}
