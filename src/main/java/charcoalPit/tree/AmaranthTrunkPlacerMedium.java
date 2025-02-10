package charcoalPit.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class AmaranthTrunkPlacerMedium extends StraightTrunkPlacer {

    public static final MapCodec<AmaranthTrunkPlacerMedium> CODEC = RecordCodecBuilder.mapCodec((p_70261_) -> {
        return trunkPlacerParts(p_70261_).apply(p_70261_, AmaranthTrunkPlacerMedium::new);
    });

    public AmaranthTrunkPlacerMedium(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTreeFeatures.AMARANTH_TRUNK_MEDIUM.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, blockSetter, random, pos.below(), config);

        for(int i = 0; i < freeTreeHeight; ++i) {
            this.placeLog(level, blockSetter, random, pos.above(i), config);
        }
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-2).north(2),config);
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-2).south(2),config);
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-2).east(2),config);
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-2).west(2),config);

        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-3).north(),config);
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-3).south(),config);
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-3).east(),config);
        this.placeLog(level,blockSetter,random,pos.above(freeTreeHeight-3).west(),config);

        int ringHeight=freeTreeHeight-7;
        if(freeTreeHeight>11)
            ringHeight--;
        if(freeTreeHeight>14)
            ringHeight--;
        if(freeTreeHeight>18)
            ringHeight--;

        this.placeLog(level,blockSetter,random,pos.above(ringHeight).north(),config);
        this.placeLog(level,blockSetter,random,pos.above(ringHeight).south(),config);
        this.placeLog(level,blockSetter,random,pos.above(ringHeight).east(),config);
        this.placeLog(level,blockSetter,random,pos.above(ringHeight).west(),config);

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false),new FoliagePlacer.FoliageAttachment(pos.above(ringHeight+1),-2,false));
    }
}
