package malekire.devilrycraft.generation.crystal_generation;

import com.mojang.serialization.Codec;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blocks.crystals.VisCrystalBlock;
import malekire.devilrycraft.util.DevilryBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class CrystalGenerationFeature extends Feature<OreFeatureConfig> {
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

                        if (VisCrystalBlock.isBlockType(positionRep, world, Blocks.CAVE_AIR) && VisCrystalBlock.isBlockType(positionRep, world, Blocks.STONE)) {

                            world.setBlockState(positionRep, DevilryBlocks.VIS_CRYSTAL_BLOCK.getBlockStateFromPos(positionRep, world).with(Properties.PICKLES, random.nextInt(2)+1), 3);
                        }
                    }
                }
            }
        }
        /*
        float f = random.nextFloat() * 3.1415927F;
        float g = (float)config.size / 8.0F;
        int i = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d = (double)pos.getX() + Math.sin((double)f) * (double)g;
        double e = (double)pos.getX() - Math.sin((double)f) * (double)g;
        double h = (double)pos.getZ() + Math.cos((double)f) * (double)g;
        double j = (double)pos.getZ() - Math.cos((double)f) * (double)g;

        double l = (double)(pos.getY() + random.nextInt(3) - 2);
        double m = (double)(pos.getY() + random.nextInt(3) - 2);
        int n = pos.getX() - MathHelper.ceil(g) - i;
        int o = pos.getY() - 2 - i;
        int p = pos.getZ() - MathHelper.ceil(g) - i;
        int q = 2 * (MathHelper.ceil(g) + i);
        int r = 2 * (2 + i);

        for(int s = n; s <= n + q; ++s) {
            for(int t = p; t <= p + q; ++t) {
                if (o <= world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
                    BlockPos positionRep = new BlockPos(s, world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t), t);
                    return world.setBlockState(positionRep, Devilrycraft.VIS_CRYSTAL_BLOCK.getBlockStateFromPos(positionRep, world), 3);
                }
            }
        }
        */
        return true;
    }
}
