package malekire.devilrycraft.blocks;


import malekire.devilrycraft.util.DevilryProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static malekire.devilrycraft.common.DevilryBlockItems.MAGICAL_CAULDRON_BLOCK_ITEM;
import static malekire.devilrycraft.common.DevilryBlockItems.VIS_PIPE_BLOCK_ITEM;
import static malekire.devilrycraft.common.DevilryTags.VIS_CONTAINER;
import static malekire.devilrycraft.util.DevilryProperties.CONNECTED_DIRECTIONS;
import static malekire.devilrycraft.util.DevilryProperties.getConnectedDirection;

public class VisPipe extends Block {

    public VisPipe(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(DevilryProperties.NORTH_CONNECTED, false)
                .with(DevilryProperties.SOUTH_CONNECTED, false).with(DevilryProperties.WEST_CONNECTED, false)
                .with(DevilryProperties.EAST_CONNECTED, false).with(DevilryProperties.UP_CONNECTED, false)
                .with(DevilryProperties.DOWN_CONNECTED, false));
    }
    public static boolean canConnect(Block block) {
        return VIS_CONTAINER.contains(block);
    }
    public BlockState getStateFromWorld(WorldAccess world, BlockPos pos)
    {
        BlockPos offsetPos = pos;
        BlockState returnState = this.getDefaultState();
        for(BooleanProperty direction : CONNECTED_DIRECTIONS)
        {
            try {
                offsetPos = pos.offset(getConnectedDirection(direction));
            } catch (Exception e) {
                e.printStackTrace();
            }
            returnState = returnState.with(direction, canConnect(world.getBlockState(offsetPos).getBlock()));
        }
        return returnState;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getStateFromWorld(ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return getStateFromWorld(world, pos);
    }
        @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        for(BooleanProperty direction : CONNECTED_DIRECTIONS)
        {
            stateManager.add(direction);
        }
    }
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        dropStack(world, pos, new ItemStack(VIS_PIPE_BLOCK_ITEM, 1));
    }


}
