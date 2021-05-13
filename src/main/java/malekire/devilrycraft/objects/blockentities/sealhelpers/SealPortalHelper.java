package malekire.devilrycraft.objects.blockentities.sealhelpers;

import com.qouteall.immersive_portals.my_util.DQuaternion;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.common.DevilrySounds;
import malekire.devilrycraft.util.math.beziercurves.BezierCurve;
import malekire.devilrycraft.util.math.beziercurves.Point;
import malekire.devilrycraft.util.portalutil.PortalFunctionUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.Level;

import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.PortalSealID;

public class SealPortalHelper extends AbstractSealHelper {
    double maxWidth = 3;
    double maxHeight = 3;
    public double width = 0;
    public double height = 0;
    public static final double INITAL_VISUAL_WIDTH = 0.04;
    public static final double INITAL_VISUAL_HEIGHT = 0.04;
    double timeValue = 0;
    public double PORTAL_OFFSET_ANIMATION_TICKS = 0.0;
    public double PORTAL_ANIMATION_TICKS = 80.0;
    BezierCurve bezierCurve = new BezierCurve();
    Portal entrancePortal;
    Portal exitPortal;
    public boolean hasPortal = false;
    public int numTriangles = 1;
    public int TICKS_TO_DESTROY = -1;
    public static final int LIGHTNING_SPAWN_CHANCE = 10;
    int ticksSinceCreation = 0;
    public boolean doSecondSound = false;
    boolean growPortal = false;
    public int tickTime = 0;
    boolean shrinkIsAnimated = false;
    public SealPortalHelper() {
        super(PortalSealID, PortalSealID.sealCombinations);
        this.isMateable = true;
        addBezierCurves();
    }
    boolean reloadExitPortal = false;
    public void addBezierCurves() {
        bezierCurve.addPoint(new Point(0, 0));
        bezierCurve.addPoint(new Point(0.5, 0));
        bezierCurve.addPoint(new Point(0.5, 1));
        bezierCurve.addPoint(new Point(1, 1));
    }
    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        if(tag.contains("exit_portal"+getPos()))
            exitPortal.fromTag(tag.getCompound("exit_portal"+getPos()));
        if(tag.contains("entrance_portal"+getPos()))
            entrancePortal.fromTag(tag.getCompound("entrance_portal"+getPos()));

        growPortal = tag.getBoolean("grow_portal");
        hasPortal = tag.getBoolean("has_portal");
        tickTime = tag.getInt("tick_time");
    }
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putBoolean("has_portal", hasPortal);
        tag.putBoolean("grow_portal", growPortal);
        tag.putInt("tick_time", tickTime);
        if(entrancePortal != null && getPos() != null) {
            tag.put("entrance_portal"+getPos(), entrancePortal.toTag(new CompoundTag()));
        }
        if(exitPortal != null && getPos() != null) {
            tag.put("exit_portal" + getPos(), exitPortal.toTag(new CompoundTag()));
        }



        return tag;
    }
    @Override
    public void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light) {

    }

    @Override
    public void tick() {

        if (tickTime > 11) {
            tickTime = 11;
        }
        /*
        if (tickTime > 3 && entrancePortal != null) {
            duringTickAnimatePortal();
            Devilrycraft.LOGGER.log(Level.INFO, "animating");
        }

         */

        tickTime++;
        /*
        if(entrancePortal == null && this.getMate() != null && ((SealPortalHelper)this.getMate()).hasPortal && tickTime > 10 && !((SealPortalHelper)this.getMate()).reloadExitPortal) {
            System.out.println("tickign mate");
            this.getMate().tick();
            ((SealPortalHelper)this.getMate()).reloadExitPortal = true;
            ((SealPortalHelper)this.getMate()).exitPortal = PortalManipulation.completeBiWayPortal(((SealPortalHelper)this.getMate()).entrancePortal, Portal.entityType);
            ((SealPortalHelper)this.getMate()).reloadPortals();
        }*/


        //System.out.println("tick time : " + tickTime + " pos : " + this.getPos() );
        if(entrancePortal != null && tickTime > 10) {
            if(!reloadExitPortal) {
                //exitPortal = PortalManipulation.completeBiWayPortal(this.entrancePortal, Portal.entityType);
                reloadExitPortal = true;
            }
        }
        if(entrancePortal == null) {
            //System.out.println(getPos());
        }
        /*
        if (entrancePortal != null && tickTime > 2) {
            if (getWorld().getClosestPlayer(entrancePortal, 3) != null || (exitPortal != null && getWorld().getClosestPlayer(exitPortal, 3) != null)) {
                Devilrycraft.LOGGER.log(Level.INFO, "is close enoguh to cause anamtion");
                if (!growPortal)
                    growPortal();
                else
                    shrinkIsAnimated = false;
            } else {
                if (!shrinkIsAnimated)
                    shrinkPortal();
            }


        }

         */


    }


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
            if(exitPortal != null) {
                PortalFunctionUtil.makeRoundPortal(exitPortal, numTriangles);
            }
        }
        if (width < 0.01) {
            return;
        }
        reloadPortals();
    }

    public void setPortalsSize(double width, double height) {
        PortalFunctionUtil.setSize(this.entrancePortal, width, height);
        if(this.exitPortal != null) {
            PortalFunctionUtil.setSize(this.exitPortal, width, height);
        }
    }

    public void reloadPortals() {
        this.entrancePortal.reloadAndSyncToClient();
        if(this.exitPortal == null || !this.exitPortal.isPortalValid() || !this.entrancePortal.isPortalValid()) {
            Devilrycraft.LOGGER.log(Level.INFO, "portal is not valid, not reloading and syncing to client");
            return;
        }
        if(getMate().getWorld().isChunkLoaded(getMate().getPos()) && this.exitPortal != null && reloadExitPortal) {
            this.exitPortal.reloadAndSyncToClient();
        }

    }

    public void setNullShape() {
        this.entrancePortal.specialShape = null;
        if(this.exitPortal != null) {
            this.exitPortal.specialShape = null;
        }
    }

    double portalDestructionAnimationTicks = PORTAL_ANIMATION_TICKS;

    public void performPortalFunction() {
        if (this.getMate() == null) {
            Devilrycraft.LOGGER.log(Level.DEBUG, "Performed portal function but no mateable seal found");
            return;
        }
        if (getWorld().getBlockState(getPos()).getBlock() == Blocks.AIR)
            return;
        BlockPos portalPosition = this.blockEntity.getPos();
        BlockPos outputPos;
        Devilrycraft.LOGGER.log(Level.INFO, "creating portal in seal gateway");
        hasPortal = true;
        outputPos = getMate().blockEntity.getPos();
        this.entrancePortal = Portal.entityType.create(getWorld());
        final float portalVisualOffset = 0.48F;

        //Sets position to center of block.
        Vec3d originPos = Vec3d.ofCenter(portalPosition);

        //Sets position to center of block.
        Vec3d destPos = Vec3d.ofCenter(outputPos);


        entrancePortal.setDestinationDimension(getMate().blockEntity.getWorld().getRegistryKey());

        originPos = PortalFunctionUtil.offsetFromFacing(originPos, blockEntity.facing, portalVisualOffset);
        destPos = PortalFunctionUtil.offsetFromFacing(destPos, getMate().getWorld().getBlockState(getMate().getPos()).get(Properties.FACING), portalVisualOffset);

        entrancePortal.setOriginPos(originPos);
        entrancePortal.setDestination(destPos);
        double rotation = PortalFunctionUtil.getDegreeFromDirectionForPortal(blockEntity.facing);
        double degrees = 180 + PortalFunctionUtil.getDegreeFromDirectionForPortal( getMate().getWorld().getBlockState(getMate().getPos()).get(Properties.FACING)) - rotation;

        entrancePortal.setRotationTransformation(DQuaternion.rotationByDegrees(new Vec3d(0, 1, 0), degrees).toMcQuaternion());
        setPortalOrientationAndSizeFromDirection(blockEntity.facing, entrancePortal, rotation);

        entrancePortal.world.spawnEntity(entrancePortal);

        exitPortal = PortalManipulation.completeBiWayPortal(this.entrancePortal, Portal.entityType);
        animatePortals(0.8);
        //exitPortal = PortalManipulation.createReversePortal(entrancePortal, Portal.entityType);
        //exitPortal.world.spawnEntity(exitPortal);

        return;


    }
    public static void setPortalOrientationAndSizeFromDirection(Direction facing, Portal portal, double rotation1) {
        //This is just the value that makes this work don't question it please lol.
        float rotation2 = 90;
        switch (facing) {
            case UP:
                portal.setOrientationAndSize(
                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation1)), // axisW
                        new Vec3d(0, 1, 0).rotateX((float) Math.toRadians(-rotation2)), // axisH
                        INITAL_VISUAL_WIDTH, // width
                        INITAL_VISUAL_HEIGHT // height
                );
                break;
            case DOWN:
                portal.setOrientationAndSize(
                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation1)), // axisW
                        new Vec3d(0, 1, 0).rotateX((float) Math.toRadians(rotation2)), // axisH
                        INITAL_VISUAL_WIDTH, // width
                        INITAL_VISUAL_HEIGHT // height
                );
                break;
            default:
                portal.setOrientationAndSize(
                        new Vec3d(1, 0, 0).rotateY((float) Math.toRadians(rotation1)), // axisW
                        new Vec3d(0, 1, 0), // axisH
                        INITAL_VISUAL_WIDTH, // width
                        INITAL_VISUAL_HEIGHT // height
                );
                break;
        }
    }

    public void shrinkPortal() {
        shrinkIsAnimated = true;
        growPortal = false;
        TICKS_TO_DESTROY = (int) ((ticksSinceCreation + 80));
        portalDestructionAnimationTicks = PORTAL_ANIMATION_TICKS;
    }

    public void playSpawnInSounds() {
        getWorld().playSound(null, getPos(), DevilrySounds.CHAOS_PORTAL, SoundCategory.BLOCKS,1F, 1F);
    }


    public void growPortal() {
        Devilrycraft.LOGGER.log(Level.INFO, "GROWING PORTAL");
        growPortal = true;
        shrinkIsAnimated = false;
        ticksSinceCreation = 0;
        TICKS_TO_DESTROY = -1;
    }

    public void animateShrinkingPortal() {
        if (shrinkIsAnimated) {
            if (ticksSinceCreation > TICKS_TO_DESTROY - (PORTAL_ANIMATION_TICKS) && !(ticksSinceCreation > TICKS_TO_DESTROY - 2)) {
                portalDestructionAnimationTicks--;
                if (timeValue > portalDestructionAnimationTicks / PORTAL_ANIMATION_TICKS)
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


            if (ticksSinceCreation == 4) {
                playSpawnInSounds();
            }
            if (ticksSinceCreation < PORTAL_ANIMATION_TICKS && ticksSinceCreation > 4 && growPortal) {
                if (timeValue < ((double) ticksSinceCreation + PORTAL_OFFSET_ANIMATION_TICKS) / (PORTAL_ANIMATION_TICKS + PORTAL_OFFSET_ANIMATION_TICKS))
                    timeValue = ((double) ticksSinceCreation + PORTAL_OFFSET_ANIMATION_TICKS) / (PORTAL_ANIMATION_TICKS + PORTAL_OFFSET_ANIMATION_TICKS);
                width = bezierCurve.getY(timeValue) * maxWidth;
                height = bezierCurve.getY(timeValue) * maxHeight;
                animatePortals(timeValue);
            }
        } else
        {
            Devilrycraft.LOGGER.log(Level.ERROR, "tried to animate portal with no portal existing");
        }
    }


    @Override
    public void oneOffTick() {
        //PortalFinderUtil.sealBlockEntities.add(blockEntity);
        performPortalFunction();
    }

    @Override
    public AbstractSealHelper getNewInstance() {
        return new SealPortalHelper();
    }
}
