package malekire.devilrycraft.generation.crystal_generation;

import com.mojang.serialization.Codec;
import malekire.devilrycraft.objects.blocks.crystals.BaseCrystalBlock;
import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public abstract class CrystalGenerationFeature extends Feature<OreFeatureConfig> {
    public CrystalGenerationFeature(Codec<OreFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos,
                            OreFeatureConfig config) {

        BlockPos topPos = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR_WG, pos);
        Direction offset = Direction.NORTH;
        BlockPos tempPos = new BlockPos(topPos.getX(), 0, topPos.getZ());
        if(random.nextBoolean()) {
            int ourY = random.nextInt(255);
            for (int y = ourY; y <= min(255, ourY + random.nextInt(7)); y++) {
                for (int x = 0; x < random.nextInt(10); x++) {
                    for (int z = 0; z < random.nextInt(10); z++) {
                        offset = offset.rotateYClockwise();
                        BlockPos positionRep = tempPos.add(x, y, z).offset(offset);

                        if (BaseCrystalBlock.isBlockType(positionRep, world, Blocks.CAVE_AIR) && BaseCrystalBlock.isBlockType(positionRep, world, Blocks.STONE)) {
                            world.setBlockState(positionRep, getBlockState(random, positionRep, world), 3);
                        }
                    }
                }
            }
        }
        return true;
    }
    public BlockState getBlockState(Random random, BlockPos pos, StructureWorldAccess world) {
        return DevilryBlocks.VIS_CRYSTAL_BLOCK.getBlockStateFromPos(pos, world).with(Properties.PICKLES, random.nextInt(3)+1);
    }

}
