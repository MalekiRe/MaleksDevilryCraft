package malekire.devilrycraft.blocks;

import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.GeometryPortalShape;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blockentities.MagicalCauldronBlockEntity;
import malekire.devilrycraft.blockentities.PortableHoleBlockEntity;
import malekire.devilrycraft.util.DevilryBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.sound.sampled.Port;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     return VoxelShapes.cuboid(0f, 0f, 0.0f, 0f, 0f, 0f);
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
        if(!world.isClient()) {

            BlockPos outputPos = null;
            boolean outputPosValid = false;
            for(int i = 0; i < BLOCK_RANGE; i++)
            {
                outputPos = pos.offset(placer.getHorizontalFacing(), i);
                if(world.getBlockState(outputPos).getBlock() == Blocks.AIR || world.getBlockState(outputPos).getBlock() == Blocks.CAVE_AIR)
                {
                    outputPosValid = true;
                    System.out.println(i);
                    outputPos = pos.offset(placer.getHorizontalFacing(), i);
                    break;
                }
            }
            if(outputPosValid) {
                Vec3d originPos = Vec3d.of(pos);
                Vec3d destPos = Vec3d.of(outputPos);

                final float portalVisualOffset = 0.06F;

                switch(placer.getHorizontalFacing()){
                    case NORTH : originPos = originPos.add(0.5, 0, portalVisualOffset); destPos = destPos.add(0.5, 0, 1-portalVisualOffset); break;
                    case SOUTH : originPos = originPos.add(0.5, 0, -portalVisualOffset+1); destPos = destPos.add(0.5, 0, portalVisualOffset); break;
                    case WEST : originPos = originPos.add(portalVisualOffset, 0, 0.5); destPos = destPos.add(-portalVisualOffset+1, 0, 0.5); break;
                    case EAST : originPos = originPos.add(-portalVisualOffset+1, 0, 0.5); destPos = destPos.add(+portalVisualOffset, 0, 0.5); break;
                }
                double rotation = 0;
                double degrees = 0;
                switch(placer.getHorizontalFacing())
                {
                    case NORTH : rotation = 0; break;
                    case SOUTH : rotation = 180; break;
                    case EAST: rotation = 270; break;
                    case WEST: rotation = 90; break;
                }
                Portal portal = Portal.entityType.create(world);
                ((PortableHoleBlockEntity) world.getBlockEntity(pos)).thePortal = portal;
                ((PortableHoleBlockEntity) world.getBlockEntity(pos)).hasPortals = true;
                portal.setOriginPos(originPos);
                portal.setDestinationDimension(world.getRegistryKey());
                portal.setDestination(destPos);
                portal.setOrientationAndSize(
                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                        new Vec3d(0, 1, 0), // axisH
                        1, // width
                        2 // height
                );


                portal.setRotationTransformation(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), degrees).toMcQuaternion());
//                portal.world.spawnEntity(portal);
//                Portal result = PortalManipulation.completeBiWayPortal(portal, Portal.entityType);
                //makeRoundPortal(result);
                portal.width = 0.01;
                portal.height = 0.01;
                world.setBlockState(new BlockPos(destPos), DevilryBlocks.PORTABLE_HOLE_BLOCK.getDefaultState().with(Properties.FACING, placer.getHorizontalFacing().getOpposite()));
                int offsetForSound = 1;


                /*
                world.playSound(
                        null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                        pos, // The position of where the sound will come from
                        Devilrycraft.CHAOS_PORTAL, // The sound that will play
                        SoundCategory.BLOCKS, // This determines which of the volume sliders affect this sound
                        0.5f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                        1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                );

                 */
                //portal.reloadAndSyncToClient();
                    //result.reloadAndSyncToClient();

                //((PortableHoleBlockEntity) world.getBlockEntity(pos)).result = result;

            }

        }
    }
    public static void makeRoundPortal(Portal portal) {
        GeometryPortalShape shape = new GeometryPortalShape();
        shape.triangles = IntStream.range(0, NUM_TRIANGLES)
                .mapToObj(i -> new GeometryPortalShape.TriangleInPlane(
                        0, 0,
                        portal.width * 0.5 * Math.cos(TAU * ((double) i) / NUM_TRIANGLES),
                        portal.height * 0.5 * Math.sin(TAU * ((double) i) / NUM_TRIANGLES),
                        portal.width * 0.5 * Math.cos(TAU * ((double) i + 1) / NUM_TRIANGLES),
                        portal.height * 0.5 * Math.sin(TAU * ((double) i + 1) / NUM_TRIANGLES)
                )).collect(Collectors.toList());
        portal.specialShape = shape;
        portal.portalTag = "portal1";
        portal.cullableXStart = portal.cullableXEnd = portal.cullableYStart = portal.cullableYEnd = 0;

    }
    public static void makeRoundPortal(Portal portal, double width, double height, int numTriangles) {
        GeometryPortalShape shape = new GeometryPortalShape();
        shape.triangles = IntStream.range(0, numTriangles)
                .mapToObj(i -> new GeometryPortalShape.TriangleInPlane(
                        0.0, 0.0,
                        width * 0.5 * Math.cos(TAU * ((double) i) / ((double) numTriangles)),
                        height * 0.5 * Math.sin(TAU * ((double) i) / ((double) numTriangles)),
                        width * 0.5 * Math.cos(TAU * ((double) (i + 1)) / ((double) numTriangles)),
                        height * 0.5 * Math.sin(TAU * ((double) (i + 1)) / ((double) numTriangles))
                )).collect(Collectors.toList());
        portal.specialShape = shape;
        portal.portalTag = "portal1";
        portal.cullableXStart = portal.cullableXEnd = portal.cullableYStart = portal.cullableYEnd = 0;

    }


}
