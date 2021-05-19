package malekire.devilrycraft.common.generation;


import com.google.common.collect.ImmutableList;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.generation.tree_generation.SilverwoodTreeGeneration;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import org.lwjgl.system.CallbackI;

public class DevilryTreeGeneration extends GenerationAbstractBase {



//    public static final Feature<TreeFeatureConfig> SILVERWOOD_TREE = new SilverwoodTreeGeneration(TreeFeatureConfig.CODEC);
//    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_CONFIGURED = DevilryTreeGeneration.SILVERWOOD_TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0)), new ForkingTrunkPlacer(10, 7, 5), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());
//    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_CONFIGURED = Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0)), new ForkingTrunkPlacer(10, 7, 5), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());
    public static final Feature<TreeFeatureConfig> SILVERWOOD_TREE = new TreeFeature(TreeFeatureConfig.CODEC);
    public static final Feature<TreeFeatureConfig> SILVERWOOD_TREE_0002 = new TreeFeature(TreeFeatureConfig.CODEC);
    public static final Feature<TreeFeatureConfig> SILVERWOOD_TREE_0003 = new TreeFeature(TreeFeatureConfig.CODEC);
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TRE_CONFIGURED = Feature.TREE.configure((new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0)), new ForkingTrunkPlacer(5, 2, 2), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_0002_CONFIGURED = Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new JungleFoliagePlacer(UniformIntDistribution.of(3), UniformIntDistribution.of(0), 2), new MegaJungleTrunkPlacer(10, 2, 19), new TwoLayersFeatureSize(1, 2, 2))).ignoreVines().build());
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_0003_CONFIGURED = Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new DarkOakFoliagePlacer(UniformIntDistribution.of(3), UniformIntDistribution.of(0)), new DarkOakTrunkPlacer(10, 2, 19), new TwoLayersFeatureSize(1, 2, 2))).ignoreVines().build());
    public static final ConfiguredFeature<?, ?> SILVERWOOD_FOREST_CONFIGURED = Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(ImmutableList.of(SILVERWOOD_TREE_0002_CONFIGURED.withChance(0.5F), SILVERWOOD_TREE_0003_CONFIGURED.withChance(0.1F)), SILVERWOOD_TRE_CONFIGURED)).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1)));


    //Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());


    static {
//        features.add(new FeatureGroup(SILVERWOOD_TREE_CONFIGURED, SILVERWOOD_TREE, new Identifier(Devilrycraft.MOD_ID, "silverwood_tree")));
        features.add(new FeatureGroup(SILVERWOOD_TRE_CONFIGURED, SILVERWOOD_TREE,  new Identifier(Devilrycraft.MOD_ID, "silverwood_tree")));
        features.add(new FeatureGroup(SILVERWOOD_TREE_0002_CONFIGURED, SILVERWOOD_TREE_0002, new Identifier(Devilrycraft.MOD_ID, "large_silverwood_tree_1")));
        features.add(new FeatureGroup(SILVERWOOD_TREE_0003_CONFIGURED, SILVERWOOD_TREE_0003, new Identifier(Devilrycraft.MOD_ID, "large_silverwood_tree_2")));
    }



}
