package malekire.devilrycraft.objects.blockentities;


import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import malekire.devilrycraft.util.portalutil.PortalFinderUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlockEntity extends BlockEntity implements Tickable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);
    }
    public Direction facing;
    int tick = 0;
    public BlockPos offsetPos;
    public BlockState blockState;
    Portal entrancePortal;
    Portal exitPortal;
    public boolean hasPortal = false;
    public boolean matchBlockState(ArrayList<CrystalType> types, BlockState state)
    {
        if(state.getBlock() == Blocks.AIR)
            return false;
        if (state.get(FIRST_LAYER) != types.get(0)) {
            return false;
        }
        if(types.size() > 1 && state.get(SECOND_LAYER) != types.get(1)) {
            return false;
        }
        if(types.size() > 2 && state.get(THIRD_LAYER) != types.get(2)) {
            return false;
        }
        if(types.size() > 3 && state.get(FOURTH_LAYER) != types.get(3)) {
            return false;
        }
        return true;
    }
    String functionItDoes = "";

    @Override
    public void tick() {

        if(tick == 0)
        {
            facing = world.getBlockState(pos).get(Properties.FACING);
            offsetPos = pos.offset(facing.getOpposite());
        }
        if(tick > 1000)
        {
            tick = 0;
        }
        tick++;
        if(!world.isClient && tick > 4)
        {
            if(world.getBlockState(offsetPos).getBlock() == Blocks.AIR)
            {
                world.breakBlock(pos, false);
            }
            if(blockState != world.getBlockState(pos))
            {
                blockState = world.getBlockState(pos);
                for(String id : SealCombinations.sealCombinations.keySet())
                {
                    if(matchBlockState(SealCombinations.sealCombinations.get(id), blockState))
                    {
                        functionItDoes = id;
                        performOneOffCodedFunction(functionItDoes);
                    }
                }
            }
            performPortalFunction();
        }
    }
    public void performOneOffCodedFunction(String functionId)
    {
        switch (functionId) {
            case "portal" : if(world.getBlockState(pos).get(FOURTH_LAYER) != CrystalType.NONE) {
                PortalFinderUtil.sealBlockEntities.add(this);
                performPortalFunction();} break;
        }
    }
    public void performCodedFunction(String functionId)
    {
        switch (functionId)
        {


        }
    }
    public void performPortalFunction() {
        if(world.getBlockState(pos).getBlock() == Blocks.AIR)
            return;
        CrystalType firstCode = world.getBlockState(pos).get(THIRD_LAYER);
        CrystalType secondCode = world.getBlockState(pos).get(FOURTH_LAYER);
        BlockPos portalPosition = getPos();
        BlockPos outputPos;
        if(hasPortal)
            return;
        for(SealBlockEntity blockEntity : PortalFinderUtil.sealBlockEntities)
        {
            if(world.getBlockState(pos).getBlock() != Blocks.AIR && blockEntity != this && world.getBlockState(blockEntity.pos).get(THIRD_LAYER) == firstCode)
            {
                if(world.getBlockState(blockEntity.pos).get(FOURTH_LAYER) == secondCode)
                {
                    hasPortal = true;
                    blockEntity.hasPortal = true;
                    outputPos = blockEntity.pos;
                    entrancePortal = Portal.entityType.create(getWorld());
                    Vec3d originPos = Vec3d.of(portalPosition);
                    Vec3d destPos = Vec3d.of(outputPos);

                    final float portalVisualOffset = 0.06F;

                    switch(facing){
                        case NORTH : originPos = originPos.add(0.5, 0, portalVisualOffset); destPos = destPos.add(0.5, 0, 1-portalVisualOffset); break;
                        case SOUTH : originPos = originPos.add(0.5, 0, -portalVisualOffset+1); destPos = destPos.add(0.5, 0, portalVisualOffset); break;
                        case WEST : originPos = originPos.add(portalVisualOffset, 0, 0.5); destPos = destPos.add(-portalVisualOffset+1, 0, 0.5); break;
                        case EAST : originPos = originPos.add(-portalVisualOffset+1, 0, 0.5); destPos = destPos.add(+portalVisualOffset, 0, 0.5); break;
                        case DOWN : originPos = originPos.add(0.5, portalVisualOffset, 1); destPos = destPos.add(0.5, -portalVisualOffset+1, 1); break;
                        case UP : originPos = originPos.add(0.5, -portalVisualOffset+1, 0); destPos = destPos.add(0.5, portalVisualOffset, 0.999); break;
                        default: break;
                    }
                    double rotation = 0;
                    double degrees = 0;
                    switch(facing)
                    {
                        case NORTH : rotation = 0; break;
                        case SOUTH : rotation = 180; break;
                        case EAST: rotation = 270; break;
                        case WEST: rotation = 90; break;
                        default: break;
                    }



                    entrancePortal.setOriginPos(originPos);
                    entrancePortal.setDestinationDimension(getWorld().getRegistryKey());
                    entrancePortal.setDestination(destPos);



                    entrancePortal.setRotationTransformation(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), degrees).toMcQuaternion());
                    float rotation2 = 90;
                    switch (facing)
                    {
                        case UP : entrancePortal.setOrientationAndSize(
                                new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                new Vec3d(0, 1, 0).rotateX((float)Math.toRadians(-rotation2)), // axisH
                                1, // width
                                2 // height
                        );break;
                        case DOWN : entrancePortal.setOrientationAndSize(
                                new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                new Vec3d(0, 1, 0).rotateX((float)Math.toRadians(rotation2)), // axisH
                                1, // width
                                2 // height
                        ); break;
                        default:
                            entrancePortal.setOrientationAndSize(
                                    new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                    new Vec3d(0, 1, 0), // axisH
                                    1, // width
                                    2 // height
                            ); break;
                    }
                    entrancePortal.width = 1;
                    entrancePortal.height = 2;
                    exitPortal = PortalManipulation.completeBiWayPortal(entrancePortal, Portal.entityType);
                    switch(blockEntity.facing)
                    {
                        case NORTH : rotation = 0; break;
                        case SOUTH : rotation = 180; break;
                        case EAST: rotation = 270; break;
                        case WEST: rotation = 90; break;
                        default: break;
                    }
                    switch (blockEntity.facing)
                    {
                        case UP : exitPortal.setOrientationAndSize(
                                new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                new Vec3d(0, 1, 0).rotateX((float)Math.toRadians(-rotation2)), // axisH
                                1, // width
                                2 // height
                        );break;
                        case DOWN : exitPortal.setOrientationAndSize(
                                new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                new Vec3d(0, 1, 0).rotateX((float)Math.toRadians(rotation2)), // axisH
                                1, // width
                                2 // height
                        ); break;
                        default:
                            exitPortal.setOrientationAndSize(
                                    new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                    new Vec3d(0, 1, 0), // axisH
                                    1, // width
                                    2 // height
                            ); break;
                    }
                    //((PortableHoleBlockEntity)context.getWorld().getBlockEntity(portalPosition)).resultPos = new BlockPos(destPos);
                }
            }
        }
    }
}
