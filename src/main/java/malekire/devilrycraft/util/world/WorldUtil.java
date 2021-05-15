package malekire.devilrycraft.util.world;

import malekire.devilrycraft.util.functional_interfaces.DoublePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
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
    public static Optional<BlockPos> findFirstBlockInRange(World world, BlockPos startPos, Vec3d range, DoublePredicate isBlockWereLookingFor) {
        BlockPos backStartingPos = startPos.subtract(new BlockPos(range.getX(), range.getY(), range.getZ()));
        BlockPos endPos = startPos.add(range.getX(), range.getY(), range.getZ());
        return findFirstBlock(world, backStartingPos, endPos, isBlockWereLookingFor);
    }

    /**
     * Finds the first block matching a type between the two positions.
     * @param world
     * @param start
     * @param end
     * @param isBlockWereLookingFor
     * @return first block found matching type
     */
    public static Optional<BlockPos> findFirstBlock(World world, BlockPos start, BlockPos end, DoublePredicate isBlockWereLookingFor) {
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
    public static Optional<BlockPos> safeFindFirstBlock(World world, BlockPos start, BlockPos end, DoublePredicate isBlockWereLookingFor) {
        BlockPos newStart = new BlockPos(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()), Math.min(start.getZ(), end.getZ()));
        BlockPos newEnd = new BlockPos(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()), Math.max(start.getZ(), end.getZ()));
        return findFirstBlock(world, newStart, newEnd, isBlockWereLookingFor);
    }

}
