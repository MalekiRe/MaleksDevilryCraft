package malekire.devilrycraft.common;

import malekire.devilrycraft.Devilrycraft;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.ArrayList;

public class DevilryTreeGeneration {
    public static ArrayList<FeatureGroup> features = new ArrayList<>();


    public static final Feature<TreeFeatureConfig> SILVERWOOD_TREE = new TreeFeature(TreeFeatureConfig.CODEC);
    public static final ConfiguredFeature<TreeFeatureConfig, ?> SILVERWOOD_TREE_CONFIGURED = Feature.TREE.configure((new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LOG.getDefaultState()), new SimpleBlockStateProvider(DevilryBlocks.SILVERWOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(UniformIntDistribution.of(2), UniformIntDistribution.of(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1))).ignoreVines().build());

    static {
        features.add(new FeatureGroup(SILVERWOOD_TREE_CONFIGURED, SILVERWOOD_TREE, new Identifier(Devilrycraft.MOD_ID, "silverwood_tree")));

    }
    public static void RegisterFeatures() {
        for(FeatureGroup featureGroup : features)
        {
            RegistryKey<ConfiguredFeature<?, ?>> configuredFeatureRegistryKey = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
                    featureGroup.name);
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, configuredFeatureRegistryKey.getValue(), featureGroup.configuredFeature);
            Registry.register(Registry.FEATURE, featureGroup.name, featureGroup.feature);
            BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Feature.RAW_GENERATION, configuredFeatureRegistryKey);
        }
    }
}
