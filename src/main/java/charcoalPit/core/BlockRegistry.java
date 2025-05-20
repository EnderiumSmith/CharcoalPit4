package charcoalPit.core;

import charcoalPit.CharcoalPit;
import charcoalPit.block.*;
import charcoalPit.tree.ModTreeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS=DeferredRegister.createBlocks(CharcoalPit.MODID);

    public static final DeferredBlock<ColoredFallingBlock> GRAVEL_TIN=BLOCKS.register("gravel_tin",
            ()->new ColoredFallingBlock(new ColorRGBA(MapColor.COLOR_LIGHT_GRAY.col), BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));
    public static final DeferredBlock<Block> ORE_TIN=BLOCKS.registerSimpleBlock("ore_tin", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<Block> ORE_PLATINUM=BLOCKS.registerSimpleBlock("ore_platinum", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<Block> ORE_DEEPSLATE_PLATINUM=BLOCKS.registerSimpleBlock("ore_deepslate_platinum", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE));
    public static final DeferredBlock<Block> ORE_CHALCOCITE=BLOCKS.registerSimpleBlock("ore_dripstone_copper", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE).sound(SoundType.DRIPSTONE_BLOCK));
    public static final DeferredBlock<Block> RAW_CHALCOCITE_BLOCK=BLOCKS.registerSimpleBlock("raw_chalcocite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK));

    //natural blocks
    public static final DeferredBlock<Block> BASALT=BLOCKS.registerSimpleBlock("basalt", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> BASALT_POLISHED=BLOCKS.registerSimpleBlock("basalt_polished", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> BASALT_BRICKS=BLOCKS.registerSimpleBlock("basalt_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<SlabBlock> BASALT_SLAB=BLOCKS.register("basalt_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<StairBlock> BASALT_STAIRS=BLOCKS.register("basalt_stairs",
            ()->new StairBlock(BASALT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<WallBlock> BASALT_WALL=BLOCKS.register("basalt_wall",
            ()->new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<RotatedPillarBlock> BASALT_PILLAR=BLOCKS.register("basalt_pillar",
            ()->new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> MARBLE=BLOCKS.registerSimpleBlock("marble", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS));
    public static final DeferredBlock<Block> MARBLE_POLISHED=BLOCKS.registerSimpleBlock("marble_polished", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS));
    public static final DeferredBlock<Block> MARBLE_BRICKS=BLOCKS.registerSimpleBlock("marble_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS));
    public static final DeferredBlock<SlabBlock> MARBLE_SLAB=BLOCKS.register("marble_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS)));
    public static final DeferredBlock<StairBlock> MARBLE_STAIRS=BLOCKS.register("marble_stairs",
            ()->new StairBlock(MARBLE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS)));
    public static final DeferredBlock<WallBlock> MARBLE_WALL=BLOCKS.register("marble_wall",
            ()->new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS)));
    public static final DeferredBlock<RotatedPillarBlock> MARBLE_PILLAR=BLOCKS.register("marble_pillar",
            ()->new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF_BRICKS)));

    public static final DeferredBlock<Block> BRONZE_BLOCK=BLOCKS.registerSimpleBlock("bronze_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> STEEL_BLOCK=BLOCKS.registerSimpleBlock("steel_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> TIN_BLOCK=BLOCKS.registerSimpleBlock("tin_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> ALUMINIUM_BLOCK=BLOCKS.registerSimpleBlock("aluminium_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> ALUMITE_BLOCK=BLOCKS.registerSimpleBlock("alumite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> PLATINUM_BLOCK=BLOCKS.registerSimpleBlock("platinum_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredBlock<Block> ENDERIUM_BLOCK=BLOCKS.registerSimpleBlock("enderium_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));

    public static final DeferredBlock<LogPileBlock> LOG_PILE=BLOCKS.register("log_pile",
            ()->new LogPileBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).noOcclusion()));

    public static final DeferredBlock<BlockAsh> WOOD_ASH=BLOCKS.register("wood_ash",
            ()->new BlockAsh(new ColorRGBA(MapColor.COLOR_LIGHT_GRAY.col), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final DeferredBlock<BlockAsh> COAL_ASH=BLOCKS.register("coal_ash",
            ()->new BlockAsh(new ColorRGBA(MapColor.COLOR_LIGHT_GRAY.col), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final DeferredBlock<ColoredFallingBlock> ASH_BLOCK=BLOCKS.register("ash_block",
            ()->new ColoredFallingBlock(new ColorRGBA(MapColor.COLOR_LIGHT_GRAY.col), BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<BlockLogPiles> ACTIVE_LOG_PILE=BLOCKS.register("active_log_pile",
            ()->new BlockLogPiles(false,1F,LOG_PILE,WOOD_ASH, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).noOcclusion()));
    public static final DeferredBlock<BlockActivePile> ACTIVE_COAL_PILE=BLOCKS.register("active_coal_pile",
            ()->new BlockActivePile(true,0.6F, BuiltInRegistries.BLOCK.wrapAsHolder(Blocks.COAL_BLOCK),COAL_ASH, BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK)));

    public static final DeferredBlock<BlockFlammable> CHARCOAL_BLOCK=BLOCKS.register("charcoal_block",
            ()->new BlockFlammable(5,5, BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).sound(SoundType.NETHER_BRICKS)));
    public static final DeferredBlock<BlockBambooCharcoal> BAMBOO_CHARCOAL=BLOCKS.register("bamboo_charcoal",
            ()->new BlockBambooCharcoal(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).sound(SoundType.NETHER_BRICKS)));
    public static final DeferredBlock<BlockFlammable> COKE_BLOCK=BLOCKS.register("coke_block",
            ()->new BlockFlammable(5,5, BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).sound(SoundType.NETHER_BRICKS)));

    public static final DeferredBlock<Block> SANDY_BRICKS=BLOCKS.registerSimpleBlock("sandy_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS));
    public static final DeferredBlock<SlabBlock> SANDY_SLAB=BLOCKS.register("sandy_slab",
            ()->new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<StairBlock> SANDY_STAIRS=BLOCKS.register("sandy_stairs",
            ()->new StairBlock(SANDY_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<WallBlock> SANDY_WALL=BLOCKS.register("sandy_wall",
            ()->new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)));
    public static final DeferredBlock<Block> HIGH_REFRACTORY_BRICKS=BLOCKS.registerSimpleBlock("high_refractory_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));

    public static final DeferredBlock<BlockBellows> BELLOWS=BLOCKS.register("bellows",
            ()->new BlockBellows(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<BlockMechanicalBellows> MECHANICAL_BELLOWS=BLOCKS.register("mechanical_bellows",
            ()->new BlockMechanicalBellows(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)));

    public static final DeferredBlock<BlockBloomeryChimney> CHIMNEY_BRICK=BLOCKS.register("chimney_brick",
            ()->new BlockBloomeryChimney(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).sound(SoundType.MUD_BRICKS).noLootTable()));
    public static final DeferredBlock<BlockBloomeryChimney> CHIMNEY_SANDY=BLOCKS.register("chimney_sandy",
            ()->new BlockBloomeryChimney(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).sound(SoundType.MUD_BRICKS).noLootTable()));
    public static final DeferredBlock<BlockBloomeryChimney> CHIMNEY_NETHER=BLOCKS.register("chimney_nether",
            ()->new BlockBloomeryChimney(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).sound(SoundType.MUD_BRICKS).noLootTable()));
    public static final DeferredBlock<BlockBloomery> BLOOMERY_BRICK=BLOCKS.register("bloomery_brick",
            ()->new BlockBloomery(CHIMNEY_BRICK,BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).sound(SoundType.MUD_BRICKS)));
    public static final DeferredBlock<BlockBloomery> BLOOMERY_SANDY=BLOCKS.register("bloomery_sandy",
            ()->new BlockBloomery(CHIMNEY_SANDY,BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).sound(SoundType.MUD_BRICKS)));
    public static final DeferredBlock<BlockBloomery> BLOOMERY_NETHER=BLOCKS.register("bloomery_nether",
            ()->new BlockBloomery(CHIMNEY_NETHER,BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).sound(SoundType.MUD_BRICKS)));

    public static final DeferredBlock<BlockBloom> BLOOM=BLOCKS.register("bloom",
            ()->new BlockBloom(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK).noLootTable()));

    public static final DeferredBlock<BlockBlastFurnace> BLAST_FURNACE=BLOCKS.register("blast_furnace_tall",
            ()->new BlockBlastFurnace(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).strength(3.5F)));
    public static final DeferredBlock<BlockBlastFurnaceMiddle> BLAST_FURNACE_MIDDLE=BLOCKS.register("blast_furnace_middle",
            ()->new BlockBlastFurnaceMiddle(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).strength(3.5F).noLootTable()));
    public static final DeferredBlock<BlockBloomeryChimney> BLAST_FURNACE_CHIMNEY=BLOCKS.register("blast_furnace_chimney",
            ()->new BlockBloomeryChimney(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).strength(3.5F).noLootTable()));

    public static final DeferredBlock<BlockCokeOven> COKE_OVEN=BLOCKS.register("coke_oven",
            ()->new BlockCokeOven(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).strength(3.5F)));

    public static final DeferredBlock<BlockLeeks> LEEKS=BLOCKS.register("leeks",
            ()->new BlockLeeks(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<DoubleCropBlock> SUNFLOWER=BLOCKS.register("sunflower",
            ()->new DoubleCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PITCHER_CROP)));

    public static final DeferredBlock<BlockDwarvenCandle> DWARVEN_CANDLE=BLOCKS.register("dwarven_candle",
            ()->new BlockDwarvenCandle(BlockBehaviour.Properties.ofFullCopy(Blocks.TNT)));

    public static final DeferredBlock<BlockBarrel> BARREL=BLOCKS.register("barrel",
            ()->new BlockBarrel(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<BlockQuern> QUERN=BLOCKS.register("quern",
            ()->new BlockQuern(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)));
    public static final DeferredBlock<BlockNestingBox> NEST_BOX=BLOCKS.register("nesting_box",
            ()->new BlockNestingBox(BlockBehaviour.Properties.ofFullCopy(Blocks.COMPOSTER)));
    public static final DeferredBlock<BlockFeedingThrough> FEEDING_THROUGH=BLOCKS.register("feeding_through",
            ()->new BlockFeedingThrough(BlockBehaviour.Properties.ofFullCopy(Blocks.COMPOSTER)));

    public static final DeferredBlock<BlockStill> STILL=BLOCKS.register("still",
            ()->new BlockStill(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final DeferredBlock<BlockStill> PRESS=BLOCKS.register("press",
            ()->new BlockPress(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<BlockBellowPump> BELLOW_PUMP=BLOCKS.register("bellow_pump",
            ()->new BlockBellowPump(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)));

    public static final DeferredBlock<BlockAikoPlush> AIKO_PLUSH=BLOCKS.register("aiko_plush",
            ()->new BlockAikoPlush(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));

    /*public static final DeferredBlock<BlockCrusher> CRUSHER=BLOCKS.register("crusher",
            ()->new BlockCrusher(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<BlockGenerator> GENERATOR=BLOCKS.register("generator",
            ()->new BlockGenerator(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));*/

    public static final DeferredBlock<BlockCeramicPot> CERAMIC_POT=BLOCKS.register("ceramic_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> WHITE_POT=BLOCKS.register("white_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> LIGHT_GRAY_POT=BLOCKS.register("light_gray_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> GRAY_POT=BLOCKS.register("gray_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> BLACK_POT=BLOCKS.register("black_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> BROWN_POT=BLOCKS.register("brown_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> RED_POT=BLOCKS.register("red_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> ORANGE_POT=BLOCKS.register("orange_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> YELLOW_POT=BLOCKS.register("yellow_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> LIME_POT=BLOCKS.register("lime_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> GREEN_POT=BLOCKS.register("green_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> CYAN_POT=BLOCKS.register("cyan_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> LIGHT_BLUE_POT=BLOCKS.register("light_blue_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> BLUE_POT=BLOCKS.register("blue_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> PURPLE_POT=BLOCKS.register("purple_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> MAGENTA_POT=BLOCKS.register("magenta_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));
    public static final DeferredBlock<BlockCeramicPot> PINK_POT=BLOCKS.register("pink_pot",
            ()->new BlockCeramicPot(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)));

    public static final DeferredBlock<SaplingBlock> APPLE_SAPLING=BLOCKS.register("apple_sapling",
            ()->new SaplingBlock(ModTreeFeatures.APPLE_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> CHERRY_SAPLING=BLOCKS.register("cherry_sapling",
            ()->new SaplingBlock(ModTreeFeatures.CHERRY_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> OLIVE_SAPLING=BLOCKS.register("olive_sapling",
            ()->new SaplingBlock(ModTreeFeatures.OLIVE_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> ORANGE_SAPLING=BLOCKS.register("orange_sapling",
            ()->new SaplingBlock(ModTreeFeatures.ORANGE_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> WALNUT_SAPLING=BLOCKS.register("walnut_sapling",
            ()->new SaplingBlock(ModTreeFeatures.WALNUT_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<SaplingBlock> DOUGLAS_SAPLING=BLOCKS.register("douglas_sapling",
            ()->new SaplingBlock(ModTreeFeatures.DOUGLAS_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<BlockAmaranthCatkin> AMARANTH_SAPLING=BLOCKS.register("amaranth_sapling",
            ()->new BlockAmaranthCatkin(ModTreeFeatures.AMARANTH_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static final DeferredBlock<BlockStrawberryBush> STRAWBERRY_BUSH=BLOCKS.register("strawberry_bush",
            ()->new BlockStrawberryBush(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<BlockFruitLeaves> APPLE_LEAVES=BLOCKS.register("apple_leaves",
            ()->new BlockFruitLeaves(BuiltInRegistries.ITEM.wrapAsHolder(Items.APPLE),0.166F, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<BlockCherryLeaves> CHERRY_LEAVES=BLOCKS.register("cherry_leaves",
            ()->new BlockCherryLeaves(ItemRegistry.CHERRY,0.333F, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<BlockFruitLeaves> OLIVE_LEAVES=BLOCKS.register("olive_leaves",
            ()->new BlockFruitLeaves(ItemRegistry.OLIVES,0.133F, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<BlockFruitLeaves> ORANGE_LEAVES=BLOCKS.register("orange_leaves",
            ()->new BlockFruitLeaves(ItemRegistry.ORANGE,0.166F, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<BlockFruitLeaves> WALNUT_LEAVES=BLOCKS.register("walnut_leaves",
            ()->new BlockFruitLeaves(ItemRegistry.WALNUT,0.166F, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<BlockLeaves> DOUGLAS_LEAVES=BLOCKS.register("douglas_leaves",
            ()->new BlockLeaves(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<BlockLeaves> AMARANTH_LEAVES=BLOCKS.register("amaranth_leaves",
            ()->new BlockLeaves(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));



}
