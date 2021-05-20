package malekire.devilrycraft.objects.fluids;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class BaseAbstractFluid extends FlowableFluid {
    /**
     * @return whether the given fluid an instance of this fluid
     */
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    /**
     * @return whether the fluid infinite like water
     */
    @Override
    protected boolean isInfinite() {
        return false;
    }

    /**
     * Perform actions when fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     */
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.getBlock().hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

//    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
//        if (this.receiveNeighborFluids2(world, pos, state)) {
//            world.getFluidTickScheduler().schedule(pos, state.getFluidState().getFluid(), this.getFlowing().getTickRate(world));
//        }
//
//    }
//
//    protected boolean receiveNeighborFluids2(World world, BlockPos pos, BlockState state) {
//
//            boolean bl = world.getBlockState(pos.down()).isOf(Blocks.SOUL_SOIL);
//            Direction[] var5 = Direction.values();
//            int var6 = var5.length;
//            System.out.println("hai");
//            for(int var7 = 0; var7 < var6; ++var7) {
//                Direction direction = var5[var7];
//                if (direction != Direction.DOWN) {
//                    BlockPos blockPos = pos.offset(direction);
//                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
//                        Block block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
//                        world.setBlockState(pos, block.getDefaultState());
//                        //this.playExtinguishSound(world, pos);
//                        return false;
//                    }
//
//                    if (bl && world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
//                        world.setBlockState(pos, Blocks.BASALT.getDefaultState());
//                        //this.playExtinguishSound(world, pos);
//                        return false;
//                    }
//                }
//            }
//
//
//        return true;
//    }

    @Override
    protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
        FluidState fluidState2 = world.getFluidState(pos);
        if (fluidState2.getFluid() instanceof WaterFluid) {
            if (state.getBlock() instanceof FluidBlock) {
                world.setBlockState(pos, Blocks.STONE.getDefaultState(), 3);
            }

            //this.playExtinguishEvent(world, pos);
            return;
        }


        super.flow(world, pos, state, direction, fluidState);

    }
    /**
     * Lava returns true if its FluidState is above a certain height and the
     * Fluid is Water.
     *
     * @return whether the given Fluid can flow into this FluidState
     */
    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    /**
     * Possibly related to the distance checks for flowing into nearby holes?
     * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
     */
    @Override
    protected int getFlowSpeed(WorldView worldView) {
        return 4;
    }

    /**
     * Water returns 1. Lava returns 2 in the Overworld and 1 in the Nether.
     */
    @Override
    protected int getLevelDecreasePerBlock(WorldView worldView) {
        return 1;
    }

    /**
     * Water returns 5. Lava returns 30 in the Overworld and 10 in the Nether.
     */
    @Override
    public int getTickRate(WorldView worldView) {
        return 5;
    }

    /**
     * Water and Lava both return 100.0F.
     */
    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }
}