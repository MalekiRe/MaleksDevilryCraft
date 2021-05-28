package malekire.devilrycraft.objects.blocks.crystals;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BaseCrystalBlock extends FacingBlock {
    float f = 8.0F - 1;
    float g = 8.0F + 1;
    float h = 8.0F - 1;
    float i = 8.0F + 1;
    public Item DROP_ITEM = Items.AIR;
    Random random = new Random();
    VoxelShape voxelShape = Block.createCuboidShape(0D, 0.0D, 0D, 1D, 1D, 1D);

    public BaseCrystalBlock(Settings settings, Item dropItem) {
        super(settings.nonOpaque());
        setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.NORTH).with(Properties.PICKLES, 1));
        this.DROP_ITEM = dropItem;
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.FACING);
        stateManager.add(Properties.PICKLES);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);
        switch(dir) {
            case NORTH:
                return VoxelShapes.cuboid(0.4f, 0.4f, 0.0f, 0.6f, 0.6f, 0.56f);
            case SOUTH:
                return VoxelShapes.cuboid(0.4f, 0.4f, 0.44f, 0.6f, 0.6f, 1f);
            case EAST:
                return VoxelShapes.cuboid(0.44f, 0.4f, 0.4f, 1f, 0.6f, 0.6f);
            case WEST:
                return VoxelShapes.cuboid(0.0f, 0.4f, 0.4f, 0.56f, .6f, 0.6f);
            case UP:
                return VoxelShapes.cuboid(0.4f, 0.44f, 0.4f, 0.6f, 1f, 0.6f);
            case DOWN:
                return VoxelShapes.cuboid(0.4f, 0.0f, 0.4f, 0.6f, .56f, 0.6f);
            default:
                return VoxelShapes.fullCube();
        }
    }
    public BlockState getBlockStateFromPos(BlockPos pos, StructureWorldAccess world) {
        if(world.getBlockState(pos.down()).getMaterial() == Material.STONE) {
            return (BlockState) this.getDefaultState().with(Properties.FACING, Direction.DOWN);
        }else if(world.getBlockState(pos.up()).getMaterial() == Material.STONE) {
            return (BlockState) this.getDefaultState().with(Properties.FACING, Direction.UP);
        }else if(world.getBlockState(pos.west()).getMaterial() == Material.STONE) {
            return (BlockState) this.getDefaultState().with(Properties.FACING, Direction.WEST);
        }else if(world.getBlockState(pos.east()).getMaterial() == Material.STONE) {
            return (BlockState) this.getDefaultState().with(Properties.FACING, Direction.EAST);
        }else if(world.getBlockState(pos.south()).getMaterial() == Material.STONE) {
            return (BlockState) this.getDefaultState().with(Properties.FACING, Direction.SOUTH);
        }else if(world.getBlockState(pos.north()).getMaterial() == Material.STONE) {
            return (BlockState) this.getDefaultState().with(Properties.FACING, Direction.NORTH);
        }else {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            return (BlockState)this.getDefaultState();
        }

    }
    public static boolean isBlockType(BlockPos pos, StructureWorldAccess world, Block block) {
        if(world.getBlockState(pos.west()).getBlock() == block)
            return true;
        if(world.getBlockState(pos.east()).getBlock() == block)
            return true;
        if(world.getBlockState(pos.south()).getBlock() == block)
            return true;
        if(world.getBlockState(pos.north()).getBlock() == block)
            return true;
        if(world.getBlockState(pos.down()).getBlock() == block)
            return true;
        if(world.getBlockState(pos.up()).getBlock() == block)
            return true;


        return false;
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        if(ctx.getWorld().getBlockState(ctx.getBlockPos().down()).getMaterial() == Material.STONE)
            return (BlockState)this.getDefaultState().with(Properties.FACING, Direction.DOWN);
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().up()).getMaterial() == Material.STONE)
            return (BlockState)this.getDefaultState().with(Properties.FACING, Direction.UP);
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().west()).getMaterial() == Material.STONE)
            return (BlockState)this.getDefaultState().with(Properties.FACING, Direction.WEST);
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().east()).getMaterial() == Material.STONE)
            return (BlockState)this.getDefaultState().with(Properties.FACING, Direction.EAST);
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().south()).getMaterial() == Material.STONE)
            return (BlockState)this.getDefaultState().with(Properties.FACING, Direction.SOUTH);
        if(ctx.getWorld().getBlockState(ctx.getBlockPos().north()).getMaterial() == Material.STONE)
            return (BlockState)this.getDefaultState().with(Properties.FACING, Direction.NORTH);

        return (BlockState)this.getDefaultState();

    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        player.addExhaustion(0.005F);
        dropStack(world, pos, new ItemStack(DROP_ITEM, state.get(Properties.PICKLES)));
    }
}
