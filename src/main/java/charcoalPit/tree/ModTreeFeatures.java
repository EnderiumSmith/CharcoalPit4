package charcoalPit.tree;

import charcoalPit.CharcoalPit;
import charcoalPit.core.BlockRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.antlr.v4.runtime.atn.SemanticContext;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ModTreeFeatures {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNKS=DeferredRegister.create(Registries.TRUNK_PLACER_TYPE,CharcoalPit.MODID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE=DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE,CharcoalPit.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>,TrunkPlacerType<?>> DOUBLE_PLACER=TRUNKS.register("double_placer",
            ()->new TrunkPlacerType<DoubleTrunkPlacer>(DoubleTrunkPlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>,FoliagePlacerType<?>> DOUGLAS_LACER=FOLIAGE.register("douglas_placer",
            ()->new FoliagePlacerType<DouglasPlacer>(DouglasPlacer.CODEC));
    public static final DeferredHolder<TrunkPlacerType<?>,TrunkPlacerType<?>> AMARANTH_TRUNK_SMALL=TRUNKS.register("amaranth_trunk_small",
            ()->new TrunkPlacerType<AmaranthTrunkPlacerSmall>(AmaranthTrunkPlacerSmall.CODEC));
    public static final DeferredHolder<TrunkPlacerType<?>,TrunkPlacerType<?>> AMARANTH_TRUNK_MEDIUM=TRUNKS.register("amaranth_trunk_medium",
            ()->new TrunkPlacerType<AmaranthTrunkPlacerMedium>(AmaranthTrunkPlacerMedium.CODEC));
    public static final DeferredHolder<TrunkPlacerType<?>,TrunkPlacerType<?>> AMARANTH_TRUNK_HUGE=TRUNKS.register("amaranth_trunk_huge",
            ()->new TrunkPlacerType<AmaranthTrunkPlacerHuge>(AmaranthTrunkPlacerHuge.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>,FoliagePlacerType<?>> AMARANTH_FOLIAGE_PLACER=FOLIAGE.register("amaranth_foliage_placer",
            ()->new FoliagePlacerType<AmaranthFoliagePlacer>(AmaranthFoliagePlacer.CODEC));

    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE=ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"apple"));
    public static final ResourceKey<ConfiguredFeature<?,?>> CHERRY=ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"cherry"));
    public static final ResourceKey<ConfiguredFeature<?,?>> OLIVE=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"olive"));
    public static final ResourceKey<ConfiguredFeature<?,?>> ORANGE=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"orange"));
    public static final ResourceKey<ConfiguredFeature<?,?>> WALNUT=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"walnut"));
    public static final ResourceKey<ConfiguredFeature<?,?>> DOUGLAS=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"douglas"));
    public static final ResourceKey<ConfiguredFeature<?,?>> DOUGLAS_TALL=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"douglas_tall"));
    public static final ResourceKey<ConfiguredFeature<?,?>> AMARANTH_SMALL=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"amaranth_small"));
    public static final ResourceKey<ConfiguredFeature<?,?>> AMARANTH_MEDIUM=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"amaranth_medium"));
    public static final ResourceKey<ConfiguredFeature<?,?>> AMARANTH_HUGE=ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(CharcoalPit.MODID,"amaranth_huge"));

    public static final TreeGrower APPLE_TREE=new TreeGrower(CharcoalPit.MODID+":apple", Optional.empty(),Optional.of(APPLE),Optional.empty());
    public static final TreeGrower CHERRY_TREE=new TreeGrower(CharcoalPit.MODID+"cherry",Optional.empty(),Optional.of(CHERRY),Optional.empty());
    public static final TreeGrower OLIVE_TREE=new TreeGrower(CharcoalPit.MODID+"olive",Optional.empty(),Optional.of(OLIVE),Optional.empty());
    public static final TreeGrower ORANGE_TREE=new TreeGrower(CharcoalPit.MODID+"orange",Optional.empty(),Optional.of(ORANGE),Optional.empty());
    public static final TreeGrower WALNUT_TREE=new TreeGrower(CharcoalPit.MODID+"walnut",Optional.of(WALNUT),Optional.empty(),Optional.empty());
    public static final TreeGrower DOUGLAS_TREE=new TreeGrower(CharcoalPit.MODID+"walnut",0.05F,Optional.empty(),Optional.empty(),Optional.of(DOUGLAS),Optional.of(DOUGLAS_TALL),Optional.empty(),Optional.empty());
    public static final TreeGrower AMARANTH_TREE=new TreeGrower(CharcoalPit.MODID+"amaranth",0.2F,Optional.of(AMARANTH_HUGE),Optional.empty(),Optional.of(AMARANTH_SMALL),Optional.of(AMARANTH_MEDIUM),Optional.empty(),Optional.empty());

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context){
        context.register(APPLE,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),new StraightTrunkPlacer(4,2,0),
                        BlockStateProvider.simple(BlockRegistry.APPLE_LEAVES.get()),new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3),new TwoLayersFeatureSize(1,0,1)).ignoreVines().build()));

        context.register(CHERRY,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.CHERRY_LOG),new StraightTrunkPlacer(4,2,0),
                        BlockStateProvider.simple(BlockRegistry.CHERRY_LEAVES.get()),new CherryFoliagePlacer(ConstantInt.of(3),ConstantInt.of(0),ConstantInt.of(5),0.25F, 0.5F, 0.16666667F, 0.33333334F),new TwoLayersFeatureSize(1,0,2)).ignoreVines().build()));

        context.register(OLIVE,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.ACACIA_LOG),new StraightTrunkPlacer(3,0,0),
                        BlockStateProvider.simple(BlockRegistry.OLIVE_LEAVES.get()),new CherryFoliagePlacer(ConstantInt.of(3),ConstantInt.of(0),ConstantInt.of(4),0.25F, 0.25F, 0F, 0F),new TwoLayersFeatureSize(1,0,2)).ignoreVines().build()));

        context.register(ORANGE,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.JUNGLE_LOG),new StraightTrunkPlacer(3,2,0),
                        BlockStateProvider.simple(BlockRegistry.ORANGE_LEAVES.get()),new CherryFoliagePlacer(ConstantInt.of(3),ConstantInt.of(0),ConstantInt.of(6),0.5F, 0.75F, 0F, 0F),new TwoLayersFeatureSize(1,0,2)).ignoreVines().build()));

        context.register(WALNUT,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.DARK_OAK_LOG),new DoubleTrunkPlacer(5,2,0),
                        BlockStateProvider.simple(BlockRegistry.WALNUT_LEAVES.get()),new DarkOakFoliagePlacer(ConstantInt.of(0),ConstantInt.of(0)),new TwoLayersFeatureSize(1,0,1)).ignoreVines().build()));

        context.register(DOUGLAS,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG),new StraightTrunkPlacer(10,9,0),
                        BlockStateProvider.simple(BlockRegistry.DOUGLAS_LEAVES.get()),new DouglasPlacer(ConstantInt.of(2),ConstantInt.of(0)),new TwoLayersFeatureSize(1,0,1)).ignoreVines().build()));

        context.register(DOUGLAS_TALL,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.SPRUCE_LOG),new StraightTrunkPlacer(20,10,0),
                        BlockStateProvider.simple(BlockRegistry.DOUGLAS_LEAVES.get()),new DouglasPlacer(ConstantInt.of(2),ConstantInt.of(0)),new TwoLayersFeatureSize(1,0,1)).ignoreVines().build()));

        context.register(AMARANTH_SMALL,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.MANGROVE_LOG),new AmaranthTrunkPlacerSmall(10,4,0),
                        BlockStateProvider.simple(BlockRegistry.AMARANTH_LEAVES.get()),new AmaranthFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0)),new TwoLayersFeatureSize(1,0,1)).decorators(ImmutableList.of(new LeaveVineDecorator(0.25F),new AttachedToLeavesDecorator(0.33F,0,0,BlockStateProvider.simple(BlockRegistry.AMARANTH_SAPLING.get().defaultBlockState().setValue(MangrovePropaguleBlock.HANGING,true)),2, List.of(Direction.DOWN)))).build()));

        context.register(AMARANTH_MEDIUM,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.MANGROVE_LOG),new AmaranthTrunkPlacerMedium(13,4,6),
                        BlockStateProvider.simple(BlockRegistry.AMARANTH_LEAVES.get()),new AmaranthFoliagePlacer(ConstantInt.of(4),ConstantInt.of(0)),new TwoLayersFeatureSize(1,0,1)).decorators(ImmutableList.of(new LeaveVineDecorator(0.25F),new AttachedToLeavesDecorator(0.33F,0,0,BlockStateProvider.simple(BlockRegistry.AMARANTH_SAPLING.get().defaultBlockState().setValue(MangrovePropaguleBlock.HANGING,true)),2, List.of(Direction.DOWN)))).build()));

        context.register(AMARANTH_HUGE,new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.MANGROVE_LOG),new AmaranthTrunkPlacerHuge(25,24,0),
                        BlockStateProvider.simple(BlockRegistry.AMARANTH_LEAVES.get()),new AmaranthFoliagePlacer(ConstantInt.of(6),ConstantInt.of(0)),new TwoLayersFeatureSize(1,0,1)).decorators(ImmutableList.of(new LeaveVineDecorator(0.25F),new AttachedToLeavesDecorator(0.33F,0,0,BlockStateProvider.simple(BlockRegistry.AMARANTH_SAPLING.get().defaultBlockState().setValue(MangrovePropaguleBlock.HANGING,true)),2, List.of(Direction.DOWN)))).build()));
    }
}
