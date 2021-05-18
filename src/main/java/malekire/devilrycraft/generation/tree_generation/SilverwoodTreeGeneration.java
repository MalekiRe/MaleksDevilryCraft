package malekire.devilrycraft.generation.tree_generation;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.*;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;


import java.util.*;

import static malekire.devilrycraft.common.DevilryBlocks.SILVERWOOD_LOG;


public class SilverwoodTreeGeneration extends Feature<TreeFeatureConfig> {
    public SilverwoodTreeGeneration(Codec<TreeFeatureConfig> codec) {
        super(codec);
    }

    public static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
        return canReplace(world, pos) || world.testBlockState(pos, (state) -> {
            return state.isIn(BlockTags.LOGS);
        });
    }

    private static boolean isVine(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            return state.isOf(Blocks.VINE);
        });
    }

    private static boolean isWater(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            return state.isOf(Blocks.WATER);
        });
    }

    public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            return state.isAir() || state.isIn(BlockTags.LEAVES);
        });
    }

    private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return block == Blocks.DIRT || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block == Blocks.COARSE_DIRT || block == Blocks.MYCELIUM || block == DevilryBlocks.SILVER_MOSS;
        });
    }

    private static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Material material = state.getMaterial();
            return material == Material.REPLACEABLE_PLANT;
        });
    }

    public static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, 19);
    }

    public static boolean canReplace(TestableWorld world, BlockPos pos) {
        return isAirOrLeaves(world, pos) || isReplaceablePlant(world, pos) || isWater(world, pos);
    }

    private boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
        int i = config.trunkPlacer.getHeight(random);
        int j = config.foliagePlacer.getRandomHeight(random, i, config);
        int k = i - j;
        int l = config.foliagePlacer.getRandomRadius(random, k);
        BlockPos blockPos2;
        int r;
        if (!config.skipFluidCheck) {
            int m = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos).getY();
            r = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos).getY();
            if (r - m > config.maxWaterDepth) {
                return false;
            }

            int q;
            if (config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
                q = m;
            } else if (config.heightmap == Heightmap.Type.WORLD_SURFACE) {
                q = r;
            } else {
                q = world.getTopPosition(config.heightmap, pos).getY();
            }

            blockPos2 = new BlockPos(pos.getX(), q, pos.getZ());
        } else {
            blockPos2 = pos;
        }

        if (blockPos2.getY() >= 1 && blockPos2.getY() + i + 1 <= 256) {
            if (!isDirtOrGrass(world, blockPos2.down())) {
                return false;
            } else {
                OptionalInt optionalInt = config.minimumSize.getMinClippedHeight();
                r = this.method_29963(world, i, blockPos2, config);
                /*
                if (r >= i || optionalInt.isPresent() && r >= optionalInt.getAsInt()) {
                    List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, random, r, blockPos2, logPositions, box, config);
                    int finalR = r;
                    list.forEach((treeNode) -> {
                        config.foliagePlacer.generate(world, random, config, finalR, treeNode, j, l, leavesPositions, box);
                    });
                    return true;
                } else {
                    return false;
                }

                 */
                int mainTrunkHeight = 12+random.nextInt(5)-3;
                for(int i3 = 0; i3 < mainTrunkHeight; i3++) {
                    world.setBlockState(pos.add(0, i3, 0), SILVERWOOD_LOG.getDefaultState(), 0);
                }
                //config.trunkPlacer.generate(world, random, mainTrunkHeight, pos, logPositions, box, config);
                return true;
            }
        } else {
            return false;
        }
    }

    private int method_29963(TestableWorld testableWorld, int i, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int j = 0; j <= i + 1; ++j) {
            int k = treeFeatureConfig.minimumSize.method_27378(i, j);

            for (int l = -k; l <= k; ++l) {
                for (int m = -k; m <= k; ++m) {
                    mutable.set((Vec3i) blockPos, l, j, m);
                    if (!canTreeReplace(testableWorld, mutable) || !treeFeatureConfig.ignoreVines && isVine(testableWorld, mutable)) {
                        return j - 2;
                    }
                }
            }
        }

        return i;
    }

    protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
        setBlockStateWithoutUpdatingNeighbors(world, pos, state);
    }
    @Override
    public final boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
        /*Set<BlockPos> set = Sets.newHashSet();
        Set<BlockPos> set2 = Sets.newHashSet();
        Set<BlockPos> set3 = Sets.newHashSet();
        BlockBox blockBox = BlockBox.empty();
        boolean bl = this.generate(structureWorldAccess, random, blockPos, set, set2, blockBox, treeFeatureConfig);
        if (blockBox.minX <= blockBox.maxX && bl && !set.isEmpty()) {
            if (!treeFeatureConfig.decorators.isEmpty()) {
                List<BlockPos> list = Lists.newArrayList((Iterable) set);
                List<BlockPos> list2 = Lists.newArrayList((Iterable) set2);
                list.sort(Comparator.comparingInt(Vec3i::getY));
                list2.sort(Comparator.comparingInt(Vec3i::getY));
                treeFeatureConfig.decorators.forEach((decorator) -> {
                    decorator.generate(structureWorldAccess, random, list, list2, set3, blockBox);
                });
            }

            VoxelSet voxelSet = this.placeLogsAndLeaves(structureWorldAccess, blockBox, set, set3);
            Structure.updateCorner(structureWorldAccess, 3, voxelSet, blockBox.minX, blockBox.minY, blockBox.minZ);
            return true;
        } else {
            return false;
        }

         */

        if (!isDirtOrGrass(structureWorldAccess, blockPos.down())) {
            return false;
        } else {
            int mainTrunkHeight = 12+random.nextInt(5)-3;
            for(int i3 = 0; i3 < mainTrunkHeight; i3++) {
                structureWorldAccess.setBlockState(blockPos.add(0, i3, 0), SILVERWOOD_LOG.getDefaultState(), 0);
            }
        }
        return true;

    }

    private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        VoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
        //int i = true;

        for (int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Iterator var9 = Lists.newArrayList((Iterable) leaves).iterator();

        BlockPos blockPos2;
        while (var9.hasNext()) {
            blockPos2 = (BlockPos) var9.next();
            if (box.contains(blockPos2)) {
                voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
            }
        }

        var9 = Lists.newArrayList((Iterable) logs).iterator();

        while (var9.hasNext()) {
            blockPos2 = (BlockPos) var9.next();
            if (box.contains(blockPos2)) {
                voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
            }

            Direction[] var11 = Direction.values();
            int var12 = var11.length;

            for (int var13 = 0; var13 < var12; ++var13) {
                Direction direction = var11[var13];
                mutable.set(blockPos2, direction);
                if (!logs.contains(mutable)) {
                    BlockState blockState = world.getBlockState(mutable);
                    if (blockState.contains(Properties.DISTANCE_1_7)) {
                        ((Set) list.get(0)).add(mutable.toImmutable());
                        setBlockStateWithoutUpdatingNeighbors(world, mutable, (BlockState) blockState.with(Properties.DISTANCE_1_7, 1));
                        if (box.contains(mutable)) {
                            voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                        }
                    }
                }
            }
        }

        for (int k = 1; k < 6; ++k) {
            Set<BlockPos> set = (Set) list.get(k - 1);
            Set<BlockPos> set2 = (Set) list.get(k);
            Iterator var25 = set.iterator();

            while (var25.hasNext()) {
                BlockPos blockPos3 = (BlockPos) var25.next();
                if (box.contains(blockPos3)) {
                    voxelSet.set(blockPos3.getX() - box.minX, blockPos3.getY() - box.minY, blockPos3.getZ() - box.minZ, true, true);
                }

                Direction[] var27 = Direction.values();
                int var28 = var27.length;

                for (int var16 = 0; var16 < var28; ++var16) {
                    Direction direction2 = var27[var16];
                    mutable.set(blockPos3, direction2);
                    if (!set.contains(mutable) && !set2.contains(mutable)) {
                        BlockState blockState2 = world.getBlockState(mutable);
                        if (blockState2.contains(Properties.DISTANCE_1_7)) {
                            int l = (Integer) blockState2.get(Properties.DISTANCE_1_7);
                            if (l > k + 1) {
                                BlockState blockState3 = (BlockState) blockState2.with(Properties.DISTANCE_1_7, k + 1);
                                setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
                                if (box.contains(mutable)) {
                                    voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                                }

                                set2.add(mutable.toImmutable());
                            }
                        }
                    }
                }
            }
        }

        return voxelSet;
    }
}
