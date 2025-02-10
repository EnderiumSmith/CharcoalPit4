package charcoalPit.tree;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class AmaranthTrunkPlacerHuge extends GiantTrunkPlacer {

    public static final MapCodec<AmaranthTrunkPlacerHuge> CODEC = RecordCodecBuilder.mapCodec((p_70189_) -> {
        return trunkPlacerParts(p_70189_).apply(p_70189_, AmaranthTrunkPlacerHuge::new);
    });

    public AmaranthTrunkPlacerHuge(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTreeFeatures.AMARANTH_TRUNK_HUGE.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        BlockPos blockpos = pos.below();
        setDirtAt(level, blockSetter, random, blockpos, config);
        setDirtAt(level, blockSetter, random, blockpos.east(), config);
        setDirtAt(level, blockSetter, random, blockpos.south(), config);
        setDirtAt(level, blockSetter, random, blockpos.south().east(), config);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        list.add(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, true));

        for(int i = 0; i < freeTreeHeight-3; ++i) {
            this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, i, 0);
            this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, i, 0);
            this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, i, 1);
            this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, i, 1);
        }
        //main branches
        //1
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -1, freeTreeHeight-3, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -1, freeTreeHeight-3, 1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, freeTreeHeight-3, -1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, freeTreeHeight-3, 2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, freeTreeHeight-3, -1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, freeTreeHeight-3, 2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 2, freeTreeHeight-3, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 2, freeTreeHeight-3, 1);
        //2
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -2, freeTreeHeight-2, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -2, freeTreeHeight-2, 1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, freeTreeHeight-2, -2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, freeTreeHeight-2, 3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, freeTreeHeight-2, -2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, freeTreeHeight-2, 3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 3, freeTreeHeight-2, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 3, freeTreeHeight-2, 1);
        //3
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -3, freeTreeHeight-1, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -3, freeTreeHeight-1, 1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, freeTreeHeight-1, -3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, freeTreeHeight-1, 4);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, freeTreeHeight-1, -3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, freeTreeHeight-1, 4);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 4, freeTreeHeight-1, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 4, freeTreeHeight-1, 1);
        //side branches
        //1
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -1, freeTreeHeight-4, -1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -1, freeTreeHeight-4, 2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 2, freeTreeHeight-4, -1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 2, freeTreeHeight-4, 2);
        //2
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -2, freeTreeHeight-3, -2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -2, freeTreeHeight-3, 3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 3, freeTreeHeight-3, -2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 3, freeTreeHeight-3, 3);
        //3
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -3, freeTreeHeight-2, -3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -3, freeTreeHeight-2, 4);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 4, freeTreeHeight-2, -3);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 4, freeTreeHeight-2, 4);
        //ring branches
        int ringHeight=(freeTreeHeight-1)/2;
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -1, ringHeight-1, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, -1, ringHeight-1, 1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, ringHeight-1, -1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 0, ringHeight-1, 2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, ringHeight-1, -1);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 1, ringHeight-1, 2);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 2, ringHeight-1, 0);
        this.placeLogIfFreeWithOffset(level, blockSetter, random, blockpos$mutableblockpos, config, pos, 2, ringHeight-1, 1);
        //little branches
        for(int i=0;i<freeTreeHeight-5;i++){
            if(i!=ringHeight&&i!=ringHeight-1&&i!=ringHeight-2){
                outer:
                for(int a=-1;a<=2;a++){
                    for(int b=-1;b<=2;b++){
                        if(random.nextInt(60)==0){
                            this.placeLogIfFreeWithOffset(level,blockSetter,random,blockpos$mutableblockpos,config,pos,a,i,b);
                            BlockPos pos2=pos.offset(a,i+1,b);
                            list.add(new FoliagePlacer.FoliageAttachment(pos2,-5,false));
                            i++;
                            break outer;
                        }
                        if(a==0||a==1){
                            b+=2;
                        }
                    }
                }
            }
        }

        list.add(new FoliagePlacer.FoliageAttachment(pos.above(ringHeight),-4,true));

        return list;
    }

    private void placeLogIfFreeWithOffset(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos offsetPos, int offsetX, int offsetY, int offsetZ) {
        pos.setWithOffset(offsetPos, offsetX, offsetY, offsetZ);
        this.placeLogIfFree(level, blockSetter, random, pos, config);
    }
}
