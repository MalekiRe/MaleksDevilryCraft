package malekire.devilrycraft.fluid_api;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

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

        boolean bl = world.getBlockState(pos.down()).isOf(Blocks.SOUL_SOIL);
        Direction[] var5 = Direction.values();
        int var6 = var5.length;
        FluidInteractionGroup fluidInteractionGroup = FluidInteractionsRegistry.fluidRegistry.get(this.fluid.getFlowing());
        if (fluidInteractionGroup == null) {
            return false;
        }
        fluidInteractionGroup.doFluidCollisionFunction(world, pos);


            /*
            for(int var7 = 0; var7 < var6; ++var7) {
                Direction direction = var5[var7];
                if (direction != Direction.DOWN) {
                    BlockPos blockPos = pos.offset(direction);
                    if (world.getFluidState(blockPos).getFluid() instanceof WaterFluid) {
                        System.out.println("HELLO THER");
                        Block block = world.getFluidState(pos).isStill() ? Blocks.SANDSTONE : Blocks.SAND;
                        world.setBlockState(pos, block.getDefaultState(), 3);
                        //this.playExtinguishSound(world, pos);
                        return false;
                    }

                    if (bl && world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                        world.setBlockState(pos, Blocks.BASALT.getDefaultState());
                        //this.playExtinguishSound(world, pos);
                        return false;
                    }
                }
            }*/


        return true;
    }
}
