package malekire.devilrycraft.blockentities;

import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.blocks.PortableHoleBlock;
import malekire.devilrycraft.util.BezierCurves.BezierCurve;
import malekire.devilrycraft.util.BezierCurves.Point;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class PortableHoleBlockEntity extends BlockEntity implements Tickable {
    public static final int TICKS_TO_DESTROY = 202;
    public Portal thePortal;
    public Portal result;
    public boolean reload = false;
    public boolean animate = true;
    double width = 0;
    double height =0;
    double maxWidth = 0.9;
    double maxHeight = 1.9;
    BezierCurve bezierCurve = new BezierCurve();
    Random random = new Random(12343);
    public boolean hasPortals = false;
    public static final double PORTAL_OFFSET_ANIMATION_TICKS = 0.0;
    public static final double PORTAL_ANIMATION_TICKS = 40.0;
    public PortableHoleBlockEntity() {
        super(Devilrycraft.PORTABLE_HOLE_BLOCK_ENTITY);
        bezierCurve.addPoint(new Point(0, 0));
        bezierCurve.addPoint(new Point(0.5, 0));
        bezierCurve.addPoint(new Point(0.5, 1));
        bezierCurve.addPoint(new Point(1, 1));



    }
    int time = 0;
    double portalDestructionAnimationTicks = PORTAL_ANIMATION_TICKS;
    @Override
    public void tick() {
        if(time > TICKS_TO_DESTROY-(PORTAL_ANIMATION_TICKS) && !(time > TICKS_TO_DESTROY-2) && this.hasPortals) {
            portalDestructionAnimationTicks--;
            double timeValue = ((double) portalDestructionAnimationTicks) / (PORTAL_ANIMATION_TICKS);
            width = bezierCurve.getY(timeValue) * ((double) maxWidth);
            height = bezierCurve.getY(timeValue) * ((double) maxHeight);
            int numTriangles = 30;
            thePortal.width = width;
            thePortal.height = height;
            result.width = width;
            result.height = height;
            if (width < 0.1) {
                numTriangles = 3;

                thePortal.reloadAndSyncToClient();
                result.reloadAndSyncToClient();
                thePortal.specialShape = null;
                result.specialShape = null;
            }
            else if (width < 0.3) {
                numTriangles = 10;

                PortableHoleBlock.makeRoundPortal(thePortal, width, height, numTriangles);
                PortableHoleBlock.makeRoundPortal(result, width, height, numTriangles);
                thePortal.reloadAndSyncToClient();
                result.reloadAndSyncToClient();
            }
            else if (width < 0.11) {
                width = 0.11;
                height = 0.11;
                numTriangles = 20;
                PortableHoleBlock.makeRoundPortal(thePortal, width, height, numTriangles);
                PortableHoleBlock.makeRoundPortal(result, width, height, numTriangles);
                thePortal.reloadAndSyncToClient();
                result.reloadAndSyncToClient();
            }
            else
            {
                PortableHoleBlock.makeRoundPortal(thePortal, width, height, numTriangles);
                PortableHoleBlock.makeRoundPortal(result, width, height, numTriangles);
                thePortal.reloadAndSyncToClient();
                result.reloadAndSyncToClient();
            }
        }
        if(!this.getWorld().isClient()) {

            if (random.nextInt(15) == 4) {

                Entity entity = Devilrycraft.SMALL_DIRECTIONAL_LIGHTNING_ENTITY.create(world);
                entity.setPos(pos.getX() + 2 * random.nextDouble() - random.nextDouble(), pos.getY() + 2 * random.nextDouble() - random.nextDouble(), pos.getZ() + random.nextDouble() - random.nextDouble());
                world.spawnEntity(entity);
            }
            if(time == 20) {
                System.out.println(this.hasPortals);

            }
            time++;
            if (this.hasPortals) {

                if (time > TICKS_TO_DESTROY) {
                    System.out.println("remove portal");


                    this.getWorld().removeBlock(result.getBlockPos(), true);
                    thePortal.remove();
                    result.remove();
                    this.getWorld().removeBlock(this.getPos(), true);
                }


                if (!reload && time > 2) {
                    thePortal.world.spawnEntity(thePortal);
                    result = PortalManipulation.completeBiWayPortal(thePortal, Portal.entityType);
                    this.thePortal.reloadAndSyncToClient();
                    this.result.reloadAndSyncToClient();
                    world.playSound(
                            null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                            this.getPos(), // The position of where the sound will come from
                            Devilrycraft.CHAOS_PORTAL, // The sound that will play
                            SoundCategory.NEUTRAL, // This determines which of the volume sliders affect this sound
                            1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                            1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                    );
                    world.playSound(
                            null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                            this.result.getBlockPos(), // The position of where the sound will come from
                            Devilrycraft.CHAOS_PORTAL, // The sound that will play
                            SoundCategory.NEUTRAL, // This determines which of the volume sliders affect this sound
                            1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                            1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
                    );

                    reload = true;
                }
                if (reload && time < PORTAL_ANIMATION_TICKS) {

                    double timeValue = ((double) time + PORTAL_OFFSET_ANIMATION_TICKS) / (PORTAL_ANIMATION_TICKS + PORTAL_OFFSET_ANIMATION_TICKS);
                    width = bezierCurve.getY(timeValue) * ((double) maxWidth);
                    height = bezierCurve.getY(timeValue) * ((double) maxHeight);
                    int numTriangles = 30;
                    thePortal.width = width;
                    thePortal.height = height;
                    result.width = width;
                    result.height = height;
                    if (width < 0.1) {
                        numTriangles = 3;

                        thePortal.reloadAndSyncToClient();
                        result.reloadAndSyncToClient();
                    }
                    else if (width < 0.3) {
                        numTriangles = 10;

                        PortableHoleBlock.makeRoundPortal(thePortal, width, height, numTriangles);
                        PortableHoleBlock.makeRoundPortal(result, width, height, numTriangles);
                        thePortal.reloadAndSyncToClient();
                        result.reloadAndSyncToClient();
                    }
                    else if (width < 0.11) {
                        width = 0.11;
                        height = 0.11;
                        numTriangles = 20;
                        PortableHoleBlock.makeRoundPortal(thePortal, width, height, numTriangles);
                        PortableHoleBlock.makeRoundPortal(result, width, height, numTriangles);
                        thePortal.reloadAndSyncToClient();
                        result.reloadAndSyncToClient();
                    }
                    else
                    {
                        PortableHoleBlock.makeRoundPortal(thePortal, width, height, numTriangles);
                        PortableHoleBlock.makeRoundPortal(result, width, height, numTriangles);
                        thePortal.reloadAndSyncToClient();
                        result.reloadAndSyncToClient();
                    }
                }
            }
        }

    }
}
