package malekire.devilrycraft.objects.blocks;
import java.util.Random;

import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.generation.DevilryTreeGeneration;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

public class SilverwoodSaplingGenerator extends SaplingGenerator {




    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return Devilrycraft.SILVERWOOD_TREE_0002_CONFIGURED;
    }


    protected ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return random.nextBoolean() ? Devilrycraft.SILVERWOOD_TREE_0002_CONFIGURED : Devilrycraft.SILVERWOOD_TREE_0003_CONFIGURED;
    }
}