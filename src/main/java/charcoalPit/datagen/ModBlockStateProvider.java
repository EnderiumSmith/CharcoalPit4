package charcoalPit.datagen;

import charcoalPit.CharcoalPit;
import charcoalPit.core.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CharcoalPit.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockRegistry.CHARCOAL_BLOCK);
        blockWithItem(BlockRegistry.COKE_BLOCK);
        simpleBlock(BlockRegistry.WOOD_ASH.get());
        simpleBlock(BlockRegistry.COAL_ASH.get());
        blockWithItem(BlockRegistry.ASH_BLOCK);
        simpleBlock(BlockRegistry.ACTIVE_COAL_PILE.get());
        blockWithItem(BlockRegistry.SANDY_BRICKS);
        stairsBlock(BlockRegistry.SANDY_STAIRS.get(), blockTexture(BlockRegistry.SANDY_BRICKS.get()));
        blockItem(BlockRegistry.SANDY_STAIRS);
        slabBlock(BlockRegistry.SANDY_SLAB.get(), blockTexture(BlockRegistry.SANDY_BRICKS.get()),blockTexture(BlockRegistry.SANDY_BRICKS.get()));
        blockItem(BlockRegistry.SANDY_SLAB);
        wallBlock(BlockRegistry.SANDY_WALL.get(), blockTexture(BlockRegistry.SANDY_BRICKS.get()));
        blockWithItem(BlockRegistry.GRAVEL_TIN);
        blockWithItem(BlockRegistry.ORE_TIN);
        blockWithItem(BlockRegistry.BASALT);
        blockWithItem(BlockRegistry.BASALT_POLISHED);
        blockWithItem(BlockRegistry.BASALT_BRICKS);
        stairsBlock(BlockRegistry.BASALT_STAIRS.get(), blockTexture(BlockRegistry.BASALT_BRICKS.get()));
        blockItem(BlockRegistry.BASALT_STAIRS);
        slabBlock(BlockRegistry.BASALT_SLAB.get(), blockTexture(BlockRegistry.BASALT_BRICKS.get()),blockTexture(BlockRegistry.BASALT_BRICKS.get()));
        blockItem(BlockRegistry.BASALT_SLAB);
        wallBlock(BlockRegistry.BASALT_WALL.get(), blockTexture(BlockRegistry.BASALT_BRICKS.get()));
        logBlock(BlockRegistry.BASALT_PILLAR.get());
        blockItem(BlockRegistry.BASALT_PILLAR);
        blockWithItem(BlockRegistry.MARBLE);
        blockWithItem(BlockRegistry.MARBLE_POLISHED);
        blockWithItem(BlockRegistry.MARBLE_BRICKS);
        stairsBlock(BlockRegistry.MARBLE_STAIRS.get(), blockTexture(BlockRegistry.MARBLE_BRICKS.get()));
        blockItem(BlockRegistry.MARBLE_STAIRS);
        slabBlock(BlockRegistry.MARBLE_SLAB.get(), blockTexture(BlockRegistry.MARBLE_BRICKS.get()),blockTexture(BlockRegistry.MARBLE_BRICKS.get()));
        blockItem(BlockRegistry.MARBLE_SLAB);
        wallBlock(BlockRegistry.MARBLE_WALL.get(), blockTexture(BlockRegistry.MARBLE_BRICKS.get()));
        logBlock(BlockRegistry.MARBLE_PILLAR.get());
        blockItem(BlockRegistry.MARBLE_PILLAR);
        blockWithItem(BlockRegistry.HIGH_REFRACTORY_BRICKS);
        blockWithItem(BlockRegistry.BRONZE_BLOCK);
        blockWithItem(BlockRegistry.STEEL_BLOCK);
        blockWithItem(BlockRegistry.TIN_BLOCK);
        blockWithItem(BlockRegistry.ALUMINIUM_BLOCK);
        blockWithItem(BlockRegistry.ALUMITE_BLOCK);
        blockWithItem(BlockRegistry.ORE_PLATINUM);
        blockWithItem(BlockRegistry.ORE_DEEPSLATE_PLATINUM);
        blockWithItem(BlockRegistry.PLATINUM_BLOCK);
        blockWithItem(BlockRegistry.ENDERIUM_BLOCK);
        blockWithItem(BlockRegistry.ORE_CHALCOCITE);
        blockWithItem(BlockRegistry.RAW_CHALCOCITE_BLOCK);
        logBlock(BlockRegistry.BAMBOO_CHARCOAL.get());
        blockItem(BlockRegistry.BAMBOO_CHARCOAL);
    }

    private void blockWithItem(DeferredBlock<?> block){
        simpleBlockWithItem(block.get(),cubeAll(block.get()));
    }

    private void blockItem(DeferredBlock<?> block){
        simpleBlockItem(block.get(),new ModelFile.UncheckedModelFile("charcoal_pit:block/"+block.getId().getPath()));
    }


}
