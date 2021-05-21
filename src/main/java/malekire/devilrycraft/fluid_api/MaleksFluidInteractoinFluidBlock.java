package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.Set;

public class MaleksFluidInteractoinFluidBlock extends FluidBlock {

    protected MaleksFluidInteractoinFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);

    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (this.receiveNeighborFluids2(world, pos, state)) {
            world.getFluidTickScheduler().schedule(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }

    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (this.receiveNeighborFluids2(world, pos, state)) {
            world.getFluidTickScheduler().schedule(pos, state.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }

    }

    protected boolean receiveNeighborFluids2(World world, BlockPos pos, BlockState state) {
        Set<FluidInteractionGroup> fluidInteractions = FluidInteractionsRegistry.fluidRegistry.get(this.fluid.getFlowing());
        if (fluidInteractions == null || fluidInteractions.size() == 0) {
            return false;
        }
        for(FluidInteractionGroup fluidInteractionGroup : fluidInteractions) {
            fluidInteractionGroup.doFluidCollisionFunction(world, pos);
        }
        return true;
    }
}
