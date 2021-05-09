package malekire.devilrycraft.objects.blockentities;


import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.common.DevilryBlockEntities;
import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.SealCombinations;
import malekire.devilrycraft.util.math.beziercurves.BezierCurve;
import malekire.devilrycraft.util.math.beziercurves.Point;
import malekire.devilrycraft.util.portalutil.PortalFinderUtil;
import malekire.devilrycraft.util.portalutil.PortalFunctionUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

import static malekire.devilrycraft.common.DevilryBlocks.SEAL_BLOCK;
import static malekire.devilrycraft.util.DevilryProperties.*;

public class SealBlockEntity extends BlockEntity implements Tickable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);
        bezierCurve.addPoint(new Point(0, 0));
        bezierCurve.addPoint(new Point(0.5, 0));
        bezierCurve.addPoint(new Point(0.5, 1));
        bezierCurve.addPoint(new Point(1, 1));
    }
    double maxWidth = 3;
    double maxHeight = 3;
    public Direction facing;
    int tick = 0;
    public BlockPos offsetPos;
    public BlockState blockState;
    Portal entrancePortal;
    Portal exitPortal;
    public boolean hasPortal = false;
    public int numTriangles = 1;

    public double width = 0;
    public double height = 0;
    double timeValue = 0;
    public double PORTAL_OFFSET_ANIMATION_TICKS = 0.0;
    public double PORTAL_ANIMATION_TICKS = 80.0;
    BezierCurve bezierCurve = new BezierCurve();
    public SealBlockEntity opposingSealBlockEntity;

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
        if(!world.isClient && tick > 1)
        {
            if(hasPortal)
                duringTickAnimatePortal();
            if(world.getBlockState(offsetPos).getBlock() == Blocks.AIR)
            {
                PortalFinderUtil.sealBlockEntities.remove(this);
                world.breakBlock(pos, false);
            }
            if(blockState != world.getBlockState(pos) || true)
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
            if(exitPortal == null && entrancePortal != null && tick > 15)
            {
                exitPortal = PortalManipulation.createReversePortal(entrancePortal, Portal.entityType);
            }
            if(entrancePortal != null && hasPortal) {
                if (world.getClosestPlayer(entrancePortal, 3) != null|| world.getClosestPlayer(exitPortal, 3) != null)
                {
                    if(!growPortal)
                        growPortal();
                }
                else
                {
                    if(!shrinkIsAnimated)
                        shrinkPortal();
                }
            }



            //performPortalFunction();
        }
    }
    public int TICKS_TO_DESTROY = -1;
    public static final int LIGHTNING_SPAWN_CHANCE = 10;
    int ticksSinceCreation = 0;
    public boolean doSecondSound = false;
    public void shrinkPortal()
    {
        shrinkIsAnimated = true;
        growPortal = false;
        TICKS_TO_DESTROY = (int) ((ticksSinceCreation + 80));
        portalDestructionAnimationTicks = PORTAL_ANIMATION_TICKS;
    }
    public void playSpawnInSounds() {

    }
    boolean growPortal = false;
    public void growPortal()
    {
        growPortal = true;
        shrinkIsAnimated = false;
        ticksSinceCreation = 0;
        TICKS_TO_DESTROY = -1;
    }
    public void animateShrinkingPortal() {
        if(shrinkIsAnimated) {
            if (ticksSinceCreation > TICKS_TO_DESTROY - (PORTAL_ANIMATION_TICKS) && !(ticksSinceCreation > TICKS_TO_DESTROY - 2)) {
                portalDestructionAnimationTicks--;
                if(timeValue > portalDestructionAnimationTicks / PORTAL_ANIMATION_TICKS)
                    timeValue = portalDestructionAnimationTicks / PORTAL_ANIMATION_TICKS;
                if (entrancePortal != null && timeValue >= 0) {
                    animatePortals(timeValue);
                }

            }
        }
    }
    public void duringTickAnimatePortal() {
        if (this.hasPortal) {
            ticksSinceCreation++;
            animateShrinkingPortal();

            if (ticksSinceCreation > TICKS_TO_DESTROY)
                //destroyItself();
            if (ticksSinceCreation == 1) {


                doSecondSound = true;
                //thePortal.commandsOnTeleported = new ArrayList<>();
                //thePortal.commandsOnTeleported.add("playsound devilry_craft:chaos_portal block "+resultPos.getX()+" "+resultPos.getY()+" "+resultPos.getZ()+" 1 1");
            }


            if(ticksSinceCreation == 4)
            {
                playSpawnInSounds();
            }
            if (ticksSinceCreation < PORTAL_ANIMATION_TICKS && ticksSinceCreation > 4 && growPortal) {
                if(timeValue < ((double) ticksSinceCreation + PORTAL_OFFSET_ANIMATION_TICKS) / (PORTAL_ANIMATION_TICKS + PORTAL_OFFSET_ANIMATION_TICKS))
                    timeValue = ((double) ticksSinceCreation + PORTAL_OFFSET_ANIMATION_TICKS) / (PORTAL_ANIMATION_TICKS + PORTAL_OFFSET_ANIMATION_TICKS);
                width = bezierCurve.getY(timeValue) * maxWidth;
                height = bezierCurve.getY(timeValue) * maxHeight;
                animatePortals(timeValue);
            }
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
    boolean shrinkIsAnimated = false;
    public void animatePortals(double timeValue) {
        width = bezierCurve.getY(timeValue) * ((double) maxWidth);
        height = bezierCurve.getY(timeValue) * ((double) maxHeight);

        setPortalsSize(width, height);
        if (width < 0.2) {
            setNullShape();
        } else {
            if (width < 0.3) {
                numTriangles = 15;
            } else if (width < 0.11) {
                numTriangles = 20;
            } else {
                numTriangles = 50;
            }
            PortalFunctionUtil.makeRoundPortal(entrancePortal, numTriangles);
            PortalFunctionUtil.makeRoundPortal(exitPortal, numTriangles);
        }
        if(width < 0.01) {
            return;
        }
        reloadPortals();
    }
    public void setPortalsSize(double width, double height) {
        PortalFunctionUtil.setSize(this.entrancePortal, width, height);
        PortalFunctionUtil.setSize(this.exitPortal, width, height);
    }

    public void reloadPortals() {
        this.entrancePortal.reloadAndSyncToClient();
        this.exitPortal.reloadAndSyncToClient();
    }

    public void setNullShape() {
        this.entrancePortal.specialShape = null;
        this.exitPortal.specialShape = null;
    }
    double portalDestructionAnimationTicks = PORTAL_ANIMATION_TICKS;
    public void performPortalFunction() {
        if(world.getBlockState(pos).getBlock() == Blocks.AIR)
            return;
        CrystalType firstCode = world.getBlockState(pos).get(THIRD_LAYER);
        CrystalType secondCode = world.getBlockState(pos).get(FOURTH_LAYER);
        BlockPos portalPosition = getPos();
        BlockPos outputPos;
        if(hasPortal)
            return;
        BlockEntity removeBlockEntity;
        for(SealBlockEntity blockEntity : PortalFinderUtil.sealBlockEntities)
        {
            if(!blockEntity.hasPortal) {

                if (world.getBlockState(blockEntity.pos).getBlock() == SEAL_BLOCK && world.getBlockState(pos).getBlock() == SEAL_BLOCK && blockEntity != this && world.getBlockState(blockEntity.pos).get(THIRD_LAYER) == firstCode) {
                    if (world.getBlockState(blockEntity.pos).get(FOURTH_LAYER) == secondCode) {
                        if (hasPortal)
                            return;
                        opposingSealBlockEntity = blockEntity;
                        System.out.println("making portal");
                        hasPortal = true;
                        blockEntity.hasPortal = false;
                        outputPos = blockEntity.pos;
                        this.entrancePortal = Portal.entityType.create(world);
                        Vec3d originPos = Vec3d.of(portalPosition).add(0, 0.5, 0);
                        Vec3d destPos = Vec3d.of(outputPos).add(0, 0.5, 0);


                        final float portalVisualOffset = 0.06F;
                        Direction reverseFacing = facing.getOpposite();

                        entrancePortal.setDestinationDimension(blockEntity.getWorld().getRegistryKey());


                        reverseFacing = facing.getOpposite();
                        switch (facing.getOpposite()) {
                            case NORTH:
                                originPos = originPos.add(0.5, 0, portalVisualOffset);
                                break;
                            case SOUTH:
                                originPos = originPos.add(0.5, 0, -portalVisualOffset + 1);
                                break;
                            case WEST:
                                originPos = originPos.add(portalVisualOffset, 0, 0.5);
                                break;
                            case EAST:
                                originPos = originPos.add(-portalVisualOffset + 1, 0, 0.5);
                                break;
                            case DOWN:
                                originPos = originPos.add(0.5, portalVisualOffset, 1);
                                break;
                            case UP:
                                originPos = originPos.add(0.5, -portalVisualOffset + 1, 0);
                                break;
                            default:
                                break;
                        }
                        switch (blockEntity.facing.getOpposite()) {
                            case NORTH:
                                destPos = destPos.add(0.5, 0, portalVisualOffset);
                                break;
                            case SOUTH:
                                destPos = destPos.add(0.5, 0, -portalVisualOffset + 1);
                                break;
                            case WEST:
                                destPos = destPos.add(portalVisualOffset, 0, 0.5);
                                break;
                            case EAST:
                                destPos = destPos.add(-portalVisualOffset + 1, 0, 0.5);
                                break;
                            case DOWN:
                                destPos = destPos.add(0.5, portalVisualOffset, 1);
                                break;
                            case UP:
                                destPos = destPos.add(0.5, -portalVisualOffset + 1, 0);
                                break;
                            default:
                                break;
                        }
                        entrancePortal.setOriginPos(originPos);
                        entrancePortal.setDestination(destPos);
                        double rotation = 0;
                        double degrees = 0;
                        switch (blockEntity.facing) {
                            case NORTH:
                                rotation = 180;
                                break;
                            case SOUTH:
                                rotation = 0;
                                break;
                            case EAST:
                                rotation = 270;
                                break;
                            case WEST:
                                rotation = 90;
                                break;
                            default:
                                break;
                        }
                        switch (facing) {
                            case NORTH:
                                degrees = 180;
                                break;
                            case SOUTH:
                                degrees = 0;
                                break;
                            case EAST:
                                degrees = 90;
                                break;
                            case WEST:
                                degrees = 270;
                                break;
                            default:
                                break;
                        }


                        entrancePortal.setRotationTransformation(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), degrees).toMcQuaternion());

                        float rotation2 = 90;

                        switch (reverseFacing) {
                            case UP:
                                entrancePortal.setOrientationAndSize(
                                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                        new Vec3d(0, 1, 0).rotateX((float) Math.toRadians(-rotation2)), // axisH
                                        0.01, // width
                                        0.01 // height
                                );
                                break;
                            case DOWN:
                                entrancePortal.setOrientationAndSize(
                                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                        new Vec3d(0, 1, 0).rotateX((float) Math.toRadians(rotation2)), // axisH
                                        0.01, // width
                                        0.01 // height
                                );
                                break;
                            default:
                                entrancePortal.setOrientationAndSize(
                                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation)), // axisW
                                        new Vec3d(0, 1, 0), // axisH
                                        0.01, // width
                                        0.01 // height
                                );
                                break;
                        }
                        entrancePortal.world.spawnEntity(entrancePortal);
                        //exitPortal = PortalManipulation.createReversePortal(entrancePortal, Portal.entityType);


                        exitPortal = PortalManipulation.completeBiWayPortal(entrancePortal, Portal.entityType);
                        //exitPortal.world.spawnEntity(exitPortal);
                        //((PortableHoleBlockEntity)context.getWorld().getBlockEntity(portalPosition)).resultPos = new BlockPos(destPos);
                        //exitPortal.world.spawnEntity(exitPortal);
                        blockEntity.exitPortal = entrancePortal;
                        blockEntity.entrancePortal = exitPortal;

                    }
                }
            }
        }
    }
}
