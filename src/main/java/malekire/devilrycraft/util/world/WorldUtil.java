package malekire.devilrycraft.util.world;

import malekire.devilrycraft.util.functional_interfaces.DoublePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WorldUtil {
    /**
     * not good for finetuned stuff, searches in a box the size of the range, the range is like the radius, it search in a diameter.
     * @param world
     * @param startPos
     * @param range
     * @param isBlockWereLookingFor
     * @return
     */
    public static Optional<BlockPos> findFirstBlockInRange(World world, BlockPos startPos, Vec3d range, BiPredicate isBlockWereLookingFor) {
        BlockPos backStartingPos = startPos.subtract(new BlockPos(range.getX(), range.getY(), range.getZ()));
        BlockPos endPos = startPos.add(range.getX(), range.getY(), range.getZ());
        return findFirstBlockUnsafe(world, backStartingPos, endPos, isBlockWereLookingFor);
    }

    /**
     * Finds the first block matching a type between the two positions.
     * @param world
     * @param start
     * @param end
     * @param isBlockWereLookingFor
     * @return first block found matching type
     */
    private static Optional<BlockPos> findFirstBlockUnsafe(World world, BlockPos start, BlockPos end, BiPredicate isBlockWereLookingFor) {
        for(int x = start.getX(); x < end.getX(); x++)
            for(int z = start.getZ(); z < end.getZ(); z++)
                for(int y = start.getY(); y < end.getY(); y++)
                    if(isBlockWereLookingFor.test(world, new BlockPos(x, y, z)))
                        return Optional.of(new BlockPos(x, y, z));
        return Optional.empty();
    }

    /**
     * Always preferable to use this one, only slightly slower.
     * finds first block matching type, but also rearanges the x, y, and z, to make sure it is a proper search, used if you aren't sure that
     * the x, y, and z values of the start pos are all less than that of the end pos.
     * @param world
     * @param start
     * @param end
     * @param isBlockWereLookingFor
     * @return first block found matching type
     */
    public static Optional<BlockPos> findFirstBlock(World world, BlockPos start, BlockPos end, BiPredicate isBlockWereLookingFor) {
        return findFirstBlock(world, getMinimumPos(start, end), getMaximumPos(start, end), isBlockWereLookingFor);
    }

    private static void performFunctionOnBlocksUnsafe(World world, BlockPos start, BlockPos end, BiConsumer action) {
        for(int x = start.getX(); x < end.getX(); x++)
            for(int z = start.getZ(); z < end.getZ(); z++)
                for(int y = start.getY(); y < end.getY(); y++)
                    action.accept(world, new BlockPos(x, y, z));
    }

    /**
     * Performs whatever function desired from the min to max possible range on all blocks in said range.
     * @param world
     * @param start
     * @param end
     * @param action Takes in a World and BlockPos to perform an action.
     */
    public static void performFunctionOnBlocks(World world, BlockPos start, BlockPos end, BiConsumer action) {
        performFunctionOnBlocksUnsafe(world, getMinimumPos(start, end), getMaximumPos(start, end), action);
    }
    /**
     * Gets the minimum possible position from taking the smallest x, y, and z from both block poses.
     * @param pos1
     * @param pos2
     * @return The minimum possible position.
     */
    public static BlockPos getMinimumPos(BlockPos pos1, BlockPos pos2) {
        return new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
    }
    /**
     * Gets the maximum possible position from taking the largest x, y, and z from both block poses.
     * @param pos1
     * @param pos2
     * @return The maximum possible position.
     */
    public static BlockPos getMaximumPos(BlockPos pos1, BlockPos pos2) {
        return new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
    }

}
