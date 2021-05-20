package malekire.devilrycraft.objects.blocks;

import malekire.devilrycraft.common.DevilryBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Properties;
import java.util.Random;

import static malekire.devilrycraft.Devilrycraft.mossSpreadRate;

public class DevilryMoss extends GrassBlock {
    public static IntProperty conversionState = IntProperty.of("conversion", 0, 2);
    int ran;

    public DevilryMoss(Settings settings) {
        super(settings);
        this.setDefaultState(((BlockState) ((BlockState) this.stateManager.getDefaultState().with(conversionState, 0).with(SNOWY, false))));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(conversionState, SNOWY);
    }
    private int spreadTicks;
    int conSate = this.conversionState.computeHashCode();
    private static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = worldView.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && (Integer)blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(worldView, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(worldView, blockPos));
            return i < worldView.getMaxLightLevel();
        }
    }

    private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, worldView, pos) && !worldView.getFluidState(blockPos).isIn(FluidTags.WATER);

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        spreadTicks++;
        if(spreadTicks >= mossSpreadRate) {
            ran++;
            //idiot me
//            System.out.println("kill me1");
            if(ran == 1){
                world.setBlockState(pos, this.getDefaultState().with(conversionState, 1));
                System.out.println("kill me2");
            }else if(ran == 2){
                world.setBlockState(pos, this.getDefaultState().with(conversionState, 0));
                System.out.println("kill me3");

            }else if(ran>2){
                ran=0;

            }
//            System.out.println("kill me4");
            if (!canSurvive(state, world, pos)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            } else {
                if (world.getLightLevel(pos.up()) >= 9) {
                    BlockState blockState = this.getDefaultState();

                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        if (world.getBlockState(blockPos).isOf(Blocks.DIRT) || world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) && canSpread(blockState, world, blockPos)) {
                            world.setBlockState(blockPos, (BlockState) blockState.with(conversionState, 2));
                        }
                    }
                }

            }
            spreadTicks = 0;
        }
    }

}
