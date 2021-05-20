package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class FluidSetBlockInteraction extends FluidInteractionGroup{
    private final BlockState blockState;
    public FluidSetBlockInteraction(Fluid mainFluid, Fluid secondaryFluid, Identifier fluidInteractionId, BlockState blockState) {
        super(mainFluid, secondaryFluid, fluidInteractionId);
        this.blockState = blockState;
    }

    @Override
    public void doFluidCollisionFunction(World world, BlockPos pos) {
        Direction[] var5 = Direction.values();
        int var6 = var5.length;
        for (int var7 = 0; var7 < var6; ++var7) {
            Direction direction = var5[var7];
            if (direction == Direction.DOWN) {
                continue;
            }
            BlockPos blockPos = pos.offset(direction);
            if (this.secondaryFluid.getBucketItem() != world.getFluidState(blockPos).getFluid().getBucketItem()) {
                Devilrycraft.LOGGER.log(Level.INFO, "fluid interaction bucket item was : " + this.secondaryFluid);
                Devilrycraft.LOGGER.log(Level.INFO, "world bucket item was : " + world.getFluidState(blockPos).getFluid());
                continue;
            }
            world.setBlockState(pos, blockState, 3);
            Devilrycraft.LOGGER.log(Level.INFO, "doing fluid interaction");
        }

    }

}
