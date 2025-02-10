package charcoalPit.datagen;

import charcoalPit.core.BlockRegistry;
import charcoalPit.core.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistry.CHARCOAL_BLOCK.get());
        dropSelf(BlockRegistry.COKE_BLOCK.get());
        dropSelf(BlockRegistry.ASH_BLOCK.get());
        dropSelf(BlockRegistry.LOG_PILE.get());
        dropOther(BlockRegistry.ACTIVE_COAL_PILE.get(), Blocks.COAL_BLOCK);
        dropOther(BlockRegistry.ACTIVE_LOG_PILE.get(), BlockRegistry.LOG_PILE.get());
        dropSelf(BlockRegistry.BLOOMERY_BRICK.get());
        dropSelf(BlockRegistry.BLOOMERY_SANDY.get());
        dropSelf(BlockRegistry.BLOOMERY_NETHER.get());
        dropSelf(BlockRegistry.BELLOWS.get());
        dropSelf(BlockRegistry.SANDY_BRICKS.get());
        dropSelf(BlockRegistry.SANDY_STAIRS.get());
        dropSelf(BlockRegistry.SANDY_WALL.get());
        add(BlockRegistry.SANDY_SLAB.get(), block->createSlabItemTable(BlockRegistry.SANDY_SLAB.get()));
        add(BlockRegistry.ORE_TIN.get(), block->createOreDrop(BlockRegistry.ORE_TIN.get(), ItemRegistry.RAW_TIN.get()));
        dropSelf(BlockRegistry.MECHANICAL_BELLOWS.get());
        dropSelf(BlockRegistry.BASALT.get());
        dropSelf(BlockRegistry.BASALT_POLISHED.get());
        dropSelf(BlockRegistry.BASALT_BRICKS.get());
        dropSelf(BlockRegistry.BASALT_STAIRS.get());
        dropSelf(BlockRegistry.BASALT_WALL.get());
        dropSelf(BlockRegistry.BASALT_PILLAR.get());
        add(BlockRegistry.BASALT_SLAB.get(), block->createSlabItemTable(BlockRegistry.BASALT_SLAB.get()));
        dropSelf(BlockRegistry.MARBLE.get());
        dropSelf(BlockRegistry.MARBLE_POLISHED.get());
        dropSelf(BlockRegistry.MARBLE_BRICKS.get());
        dropSelf(BlockRegistry.MARBLE_STAIRS.get());
        dropSelf(BlockRegistry.MARBLE_WALL.get());
        dropSelf(BlockRegistry.MARBLE_PILLAR.get());
        add(BlockRegistry.MARBLE_SLAB.get(), block->createSlabItemTable(BlockRegistry.MARBLE_SLAB.get()));
        dropSelf(BlockRegistry.DWARVEN_CANDLE.get());
        dropSelf(BlockRegistry.CREOSOTE_FUNNEL_BRICK.get());
        dropSelf(BlockRegistry.CREOSOTE_FUNNEL_SANDY.get());
        dropSelf(BlockRegistry.CREOSOTE_FUNNEL_NETHER.get());
        dropSelf(BlockRegistry.HIGH_REFRACTORY_BRICKS.get());
        dropSelf(BlockRegistry.BLAST_FURNACE.get());
        dropSelf(BlockRegistry.BRONZE_BLOCK.get());
        dropSelf(BlockRegistry.APPLE_SAPLING.get());
        dropSelf(BlockRegistry.CHERRY_SAPLING.get());
        dropSelf(BlockRegistry.OLIVE_SAPLING.get());
        dropSelf(BlockRegistry.ORANGE_SAPLING.get());
        dropSelf(BlockRegistry.WALNUT_SAPLING.get());
        dropSelf(BlockRegistry.DOUGLAS_SAPLING.get());
        dropSelf(BlockRegistry.STILL.get());
        dropSelf(BlockRegistry.STEEL_BLOCK.get());
        dropSelf(BlockRegistry.PRESS.get());
        dropSelf(BlockRegistry.TIN_BLOCK.get());
        dropSelf(BlockRegistry.QUERN.get());
        dropSelf(BlockRegistry.CERAMIC_POT.get());
        dropSelf(BlockRegistry.WHITE_POT.get());
        dropSelf(BlockRegistry.LIGHT_GRAY_POT.get());
        dropSelf(BlockRegistry.GRAY_POT.get());
        dropSelf(BlockRegistry.BLACK_POT.get());
        dropSelf(BlockRegistry.BROWN_POT.get());
        dropSelf(BlockRegistry.RED_POT.get());
        dropSelf(BlockRegistry.ORANGE_POT.get());
        dropSelf(BlockRegistry.YELLOW_POT.get());
        dropSelf(BlockRegistry.LIME_POT.get());
        dropSelf(BlockRegistry.GREEN_POT.get());
        dropSelf(BlockRegistry.CYAN_POT.get());
        dropSelf(BlockRegistry.LIGHT_BLUE_POT.get());
        dropSelf(BlockRegistry.BLUE_POT.get());
        dropSelf(BlockRegistry.PURPLE_POT.get());
        dropSelf(BlockRegistry.MAGENTA_POT.get());
        dropSelf(BlockRegistry.PINK_POT.get());
        dropSelf(BlockRegistry.NEST_BOX.get());
        dropSelf(BlockRegistry.FEEDING_THROUGH.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return List.of(BlockRegistry.CHARCOAL_BLOCK.get(),BlockRegistry.COKE_BLOCK.get(),BlockRegistry.ASH_BLOCK.get(),BlockRegistry.LOG_PILE.get(),
                BlockRegistry.ACTIVE_COAL_PILE.get(),BlockRegistry.ACTIVE_LOG_PILE.get(),BlockRegistry.BLOOMERY_BRICK.get(),BlockRegistry.BLOOMERY_SANDY.get(),BlockRegistry.BLOOMERY_NETHER.get(),
                BlockRegistry.BELLOWS.get(),BlockRegistry.SANDY_BRICKS.get(),BlockRegistry.SANDY_SLAB.get(),BlockRegistry.SANDY_STAIRS.get(),BlockRegistry.SANDY_WALL.get(),
                BlockRegistry.ORE_TIN.get(),BlockRegistry.MECHANICAL_BELLOWS.get(),BlockRegistry.BASALT.get(),BlockRegistry.BASALT_POLISHED.get(),BlockRegistry.BASALT_BRICKS.get(),
                BlockRegistry.BASALT_SLAB.get(),BlockRegistry.BASALT_STAIRS.get(),BlockRegistry.BASALT_WALL.get(),BlockRegistry.BASALT_PILLAR.get(),
                BlockRegistry.MARBLE.get(),BlockRegistry.MARBLE_POLISHED.get(),BlockRegistry.MARBLE_BRICKS.get(),BlockRegistry.MARBLE_SLAB.get(),BlockRegistry.MARBLE_STAIRS.get(),
                BlockRegistry.MARBLE_WALL.get(),BlockRegistry.MARBLE_PILLAR.get(),BlockRegistry.DWARVEN_CANDLE.get(),
                BlockRegistry.CREOSOTE_FUNNEL_BRICK.get(),BlockRegistry.CREOSOTE_FUNNEL_SANDY.get(),BlockRegistry.CREOSOTE_FUNNEL_NETHER.get(),BlockRegistry.HIGH_REFRACTORY_BRICKS.get(),
                BlockRegistry.BLAST_FURNACE.get(),BlockRegistry.BRONZE_BLOCK.get(),BlockRegistry.APPLE_SAPLING.get(),BlockRegistry.CHERRY_SAPLING.get(),
                BlockRegistry.OLIVE_SAPLING.get(),BlockRegistry.ORANGE_SAPLING.get(),BlockRegistry.WALNUT_SAPLING.get(),BlockRegistry.DOUGLAS_SAPLING.get(),BlockRegistry.STILL.get(),
                BlockRegistry.STEEL_BLOCK.get(),BlockRegistry.PRESS.get(),BlockRegistry.TIN_BLOCK.get(),BlockRegistry.QUERN.get(),
                BlockRegistry.CERAMIC_POT.get(),BlockRegistry.WHITE_POT.get(),BlockRegistry.LIGHT_GRAY_POT.get(),BlockRegistry.GRAY_POT.get(),BlockRegistry.BLACK_POT.get(),
                BlockRegistry.BROWN_POT.get(),BlockRegistry.RED_POT.get(),BlockRegistry.ORANGE_POT.get(),BlockRegistry.YELLOW_POT.get(),BlockRegistry.LIME_POT.get(),
                BlockRegistry.GREEN_POT.get(),BlockRegistry.CYAN_POT.get(),BlockRegistry.LIGHT_BLUE_POT.get(),BlockRegistry.BLUE_POT.get(),BlockRegistry.PURPLE_POT.get(),
                BlockRegistry.MAGENTA_POT.get(),BlockRegistry.PINK_POT.get(),BlockRegistry.NEST_BOX.get(),BlockRegistry.FEEDING_THROUGH.get());
    }
}
