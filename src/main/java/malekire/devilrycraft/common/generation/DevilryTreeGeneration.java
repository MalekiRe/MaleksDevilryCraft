package malekire.devilrycraft.common.generation;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilryBlocks;
import malekire.devilrycraft.generation.tree_generation.SilverwoodTreeGeneration;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.AcaciaFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;

public class DevilryTreeGeneration extends GenerationAbstractBase {



    public static final Feature<TreeFeatureConfig> SILVERWOOD_TREE = new SilverwoodTreeGeneration(TreeFeatureConfig.CODEC);
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_CONFIGURED = DevilryTreeGeneration.SILVERWOOD_TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new AcaciaFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0)), new ForkingTrunkPlacer(10, 7, 5), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build());



        //Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());


    static {
        features.add(new FeatureGroup(SILVERWOOD_TREE_CONFIGURED, SILVERWOOD_TREE, new Identifier(Devilrycraft.MOD_ID, "silverwood_tree")));

    }



}
