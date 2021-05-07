package malekire.devilrycraft.objects.blocks;

import com.tfc.minecraft_effekseer_implementation.meifabric.NetworkingFabric;
import malekire.devilrycraft.objects.blockentities.BoreBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BoreBlock extends BlockWithEntity {
    public BoreBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH));
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(Properties.FACING, ctx.getPlayerFacing());
    }
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient) {
            ((BoreBlockEntity)world.getBlockEntity(pos)).destroyEffect();
            //((BasicInfuserBlockEntity)world.getBlockEntity(pos)).efk.delete(((BasicInfuserBlockEntity)world.getBlockEntity(pos)).emitter);
        }
        super.onBreak(world, pos, state, player);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.FACING);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new BoreBlockEntity();
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }
}
