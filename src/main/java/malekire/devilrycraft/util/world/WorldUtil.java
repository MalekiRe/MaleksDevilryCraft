package malekire.devilrycraft.util.world;

import malekire.devilrycraft.util.functional_interfaces.DoublePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class WorldUtil {
    public static Optional<BlockPos> findFirstBlockInRange(World world, BlockPos startPos, Vec3d range, DoublePredicate isBlockWereLookingFor) {
        BlockPos backStartingPos = startPos.subtract(new BlockPos(range.getX(), range.getY(), range.getZ()));
        BlockPos endPos = startPos.add(range.getX(), range.getY(), range.getZ());
       /*
        System.out.println("start pos " + startPos);
        System.out.println("end pos " + endPos);
        */
        for(int x = backStartingPos.getX(); x < endPos.getX(); x++) {
            for(int y = backStartingPos.getY(); y < endPos.getY(); y++) {
                for(int z = backStartingPos.getZ(); z < endPos.getZ(); z++) {
                    //System.out.println(world.getBlockState(new BlockPos(x, y, z)).getBlock());
                    if(isBlockWereLookingFor.test(world, new BlockPos(x, y, z))) {
                        return Optional.of(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return Optional.empty();
    }
}
