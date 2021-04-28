package malekire.devilrycraft.generation.crystal_generation;

import com.mojang.serialization.Codec;
import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Random;

public class EarthCrystalGenerationFeature extends CrystalGenerationFeature{
    public EarthCrystalGenerationFeature(Codec<OreFeatureConfig> config) {
        super(config);
    }
    public BlockState getBlockState(Random random, BlockPos pos, StructureWorldAccess world) {
        return DevilryBlocks.EARTH_CRYSTAL_BLOCK.getBlockStateFromPos(pos, world).with(Properties.PICKLES, random.nextInt(3)+1);
    }
}
