package charcoalPit.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class DouglasPlacer extends FoliagePlacer {

    public static final MapCodec<DouglasPlacer> CODEC= RecordCodecBuilder.mapCodec((arg1)->{
        return foliagePlacerParts(arg1).apply(arg1,DouglasPlacer::new);
    });

    public DouglasPlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModTreeFeatures.DOUGLAS_LACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource pRandom, TreeConfiguration pConfig, int i, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        int par4=pAttachment.pos().getY();
        int t=0;
        for(int k1=par4+pFoliageHeight/3-1;k1<=par4+pFoliageHeight-1;k1++){
            int k2 = k1 - (par4 + pFoliageHeight);
            int z=pFoliageHeight;
            if (pFoliageHeight>20)
                z=20;
            int x = z/10 +1;
            if (k1-par4>pFoliageHeight/2||k1-par4-(pFoliageHeight/3)+2<3)
                x--;
            if(par4+pFoliageHeight-k1<4)
                x=1;
            if(x==z/10+1)
                t++;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int j = -x; j <= x; ++j) {
                for(int k = -x; k <= x; ++k) {
                    if ((j!=0||k!=0&&k2!=0)&&
                            (Math.abs(j)+Math.abs(k)!=x*2||
                                    k1-par4>pFoliageHeight/2&&k1-par4<(4*pFoliageHeight/5)||
                                    k1-par4-pFoliageHeight/3+2==2)&&pRandom.nextInt(20)!=0) {
                        blockpos$mutableblockpos.setWithOffset(pAttachment.pos(), j, k2, k);
                        tryPlaceLeaf(levelSimulatedReader, foliageSetter, pRandom, pConfig, blockpos$mutableblockpos);
                    }
                }
            }
        }
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for(int k3=pOffset;k3<pOffset+t;k3++){
            blockpos$mutableblockpos.setWithOffset(pAttachment.pos(), 0, k3, 0);
            tryPlaceLeaf(levelSimulatedReader, foliageSetter, pRandom, pConfig, blockpos$mutableblockpos);
        }
    }

    @Override
    public int foliageHeight(RandomSource randomSource, int i, TreeConfiguration treeConfiguration) {
        return i;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource randomSource, int i, int i1, int i2, int i3, boolean b) {
        return false;
    }
}
