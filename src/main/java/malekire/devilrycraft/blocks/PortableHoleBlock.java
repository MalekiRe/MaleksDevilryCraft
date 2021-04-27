package malekire.devilrycraft.blocks;

import malekire.devilrycraft.blockentities.PortableHoleBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PortableHoleBlock extends Block implements BlockEntityProvider {
    public static final int BLOCK_RANGE = 300;
    public static final int NUM_TRIANGLES = 5;
    private static final double TAU = 2*Math.PI;

    public PortableHoleBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.FACING);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
     return VoxelShapes.empty();
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        PortableHoleBlockEntity entity = new PortableHoleBlockEntity();
        return entity;
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(Properties.FACING, ctx.getPlayerFacing());

    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
    }



}
