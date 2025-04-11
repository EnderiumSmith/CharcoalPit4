package charcoalPit.core;

import charcoalPit.CharcoalPit;
import charcoalPit.tile.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class TileEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CharcoalPit.MODID);

    public static final Supplier<BlockEntityType<TileBloomery>> BLOOMERY=TILE_ENTITIES.register("bloomery",
            ()->BlockEntityType.Builder.of(TileBloomery::new,BlockRegistry.BLOOMERY_BRICK.get(),BlockRegistry.BLOOMERY_SANDY.get(),BlockRegistry.BLOOMERY_NETHER.get()).build(null));
    public static final Supplier<BlockEntityType<TileBloom>> BLOOM=TILE_ENTITIES.register("bloom",
            ()->BlockEntityType.Builder.of(TileBloom::new,BlockRegistry.BLOOM.get()).build(null));
    public static final Supplier<BlockEntityType<TileBlastFurnace>> BLAST_FURNACE=TILE_ENTITIES.register("blast_furnace",
            ()->BlockEntityType.Builder.of(TileBlastFurnace::new,BlockRegistry.BLAST_FURNACE.get()).build(null));
    public static final Supplier<BlockEntityType<TileBarrel>> BARREL=TILE_ENTITIES.register("barrel",
            ()->BlockEntityType.Builder.of(TileBarrel::new,BlockRegistry.BARREL.get()).build(null));
    public static final Supplier<BlockEntityType<TIleStill>> STILL=TILE_ENTITIES.register("still",
            ()->BlockEntityType.Builder.of(TIleStill::new,BlockRegistry.STILL.get()).build(null));
    public static final Supplier<BlockEntityType<TilePress>> PRESS=TILE_ENTITIES.register("press",
            ()->BlockEntityType.Builder.of(TilePress::new, BlockRegistry.PRESS.get()).build(null));
    public static final Supplier<BlockEntityType<TileQuern>> QUERN=TILE_ENTITIES.register("quern",
            ()->BlockEntityType.Builder.of(TileQuern::new, BlockRegistry.QUERN.get()).build(null));
    public static final Supplier<BlockEntityType<TIleCeramicPot>> CERAMIC_POT=TILE_ENTITIES.register("ceramic_pot",
            ()->BlockEntityType.Builder.of(TIleCeramicPot::new, BlockRegistry.CERAMIC_POT.get(),BlockRegistry.WHITE_POT.get(),BlockRegistry.LIGHT_GRAY_POT.get(),
                    BlockRegistry.GRAY_POT.get(),BlockRegistry.BLACK_POT.get(),BlockRegistry.BROWN_POT.get(),BlockRegistry.RED_POT.get(),BlockRegistry.ORANGE_POT.get(),
                    BlockRegistry.YELLOW_POT.get(),BlockRegistry.LIME_POT.get(),BlockRegistry.GREEN_POT.get(),BlockRegistry.CYAN_POT.get(),BlockRegistry.LIGHT_BLUE_POT.get(),
                    BlockRegistry.BLUE_POT.get(),BlockRegistry.PURPLE_POT.get(),BlockRegistry.MAGENTA_POT.get(),BlockRegistry.PINK_POT.get()).build(null));
    public static final Supplier<BlockEntityType<TileNestingBox>> NESTING_BOX=TILE_ENTITIES.register("nesting_box",
            ()->BlockEntityType.Builder.of(TileNestingBox::new, BlockRegistry.NEST_BOX.get()).build(null));
    public static final Supplier<BlockEntityType<TileFeedingThrough>> FEEDING_THROUGH=TILE_ENTITIES.register("feeding_through",
            ()->BlockEntityType.Builder.of(TileFeedingThrough::new,BlockRegistry.FEEDING_THROUGH.get()).build(null));
    public static final Supplier<BlockEntityType<TileCokeOven>> COKE_OVEN=TILE_ENTITIES.register("coke_oven",
            ()->BlockEntityType.Builder.of(TileCokeOven::new, BlockRegistry.COKE_OVEN.get()).build(null));


}
