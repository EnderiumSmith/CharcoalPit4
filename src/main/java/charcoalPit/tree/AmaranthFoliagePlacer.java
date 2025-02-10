package charcoalPit.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class AmaranthFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<AmaranthFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((p_68473_) -> {
        return foliagePlacerParts(p_68473_).apply(p_68473_, AmaranthFoliagePlacer::new);
    });

    public AmaranthFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModTreeFeatures.AMARANTH_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i, FoliageAttachment foliageAttachment, int foliageHeight, int foliageRadius, int offset) {
        if(foliageAttachment.radiusOffset()<0){
            placeLeavesRow(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos(),foliageRadius+foliageAttachment.radiusOffset(),0,foliageAttachment.doubleTrunk());
        }else{
            placeLeavesRow(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos(),foliageRadius>2?foliageRadius-2:foliageRadius-1,0,foliageAttachment.doubleTrunk());
            placeLeavesRow(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos(),foliageRadius,-1,foliageAttachment.doubleTrunk());
            placeLeavesRow(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos(),foliageRadius-1,-2,foliageAttachment.doubleTrunk());
            if(foliageRadius==2){
                tryPlaceLeaf(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos().below().north(2).west(2));
                tryPlaceLeaf(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos().below().north(2).east(2));
                tryPlaceLeaf(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos().below().south(2).west(2));
                tryPlaceLeaf(levelSimulatedReader,foliageSetter,randomSource,treeConfiguration,foliageAttachment.pos().below().south(2).east(2));
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource randomSource, int i, TreeConfiguration treeConfiguration) {
        return 3;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource randomSource, int localX, int localY, int localZ, int range, boolean large) {
        if(localX==range||localZ==range){
            int sum=localX+localZ;
            if(range>4)
                return sum>range+2;
            else
                return sum>range+1;
        }
        return false;
    }
}
