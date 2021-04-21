package malekire.devilrycraft.blockentities;

import com.mojang.authlib.GameProfile;
import com.qouteall.immersive_portals.portal.Portal;
import com.qouteall.immersive_portals.portal.PortalManipulation;
import malekire.devilrycraft.Devilrycraft;
import malekire.devilrycraft.util.math.beziercurves.BezierCurve;
import malekire.devilrycraft.util.math.beziercurves.Point;
import malekire.devilrycraft.util.portalutil.PortableHolePortal;
import malekire.devilrycraft.util.portalutil.PortalFunctionUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Random;

public class PortableHoleBlockEntity extends BlockEntity implements Tickable {
    public static final int TICKS_TO_DESTROY = 202;
    public static final int LIGHTNING_SPAWN_CHANCE = 10;
    public Portal thePortal;
    public Portal result;
    double width = 0;
    double height = 0;
    double maxWidth = 0.9;
    double maxHeight = 1.9;
    int ticksSinceCreation = 0;
    BezierCurve bezierCurve = new BezierCurve();
    Random random = new Random();
    public boolean hasPortals = false;
    public static final double PORTAL_OFFSET_ANIMATION_TICKS = 0.0;
    public static final double PORTAL_ANIMATION_TICKS = 40.0;
    int numTriangles = 30;
    boolean doSecondSound = false;
    double timeValue;
    public ArrayList<BlockPos> fabricsOfReality = new ArrayList<>();
    public BlockPos resultPos;
    public PortableHoleBlockEntity() {
        super(Devilrycraft.PORTABLE_HOLE_BLOCK_ENTITY);
        bezierCurve.addPoint(new Point(0, 0));
        bezierCurve.addPoint(new Point(0.5, 0));
        bezierCurve.addPoint(new Point(0.5, 1));
        bezierCurve.addPoint(new Point(1, 1));


    }

    public void setPortalsSize(double width, double height) {
        PortalFunctionUtil.setSize(this.thePortal, width, height);
        PortalFunctionUtil.setSize(this.result, width, height);
    }

    public void reloadPortals() {
        this.thePortal.reloadAndSyncToClient();
        this.result.reloadAndSyncToClient();
    }

    public void setNullShape() {
        this.thePortal.specialShape = null;
        this.result.specialShape = null;
    }

    public void destroyItself() {
        this.getWorld().removeBlock(result.getBlockPos(), true);
        thePortal.remove();
        result.remove();
        this.getWorld().removeBlock(this.getPos(), true);
        for(BlockPos blockPos : fabricsOfReality) {
            this.getWorld().removeBlock(blockPos, true);
        }
    }

    public void playSpawnInSounds() {
        world.playSound(
                null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                this.getPos(), // The position of where the sound will come from
                Devilrycraft.CHAOS_PORTAL, // The sound that will play
                SoundCategory.NEUTRAL, // This determines which of the volume sliders affect this sound
                1f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
        );
    }

    double portalDestructionAnimationTicks = PORTAL_ANIMATION_TICKS;

    public void animatePortals(double timeValue) {
        width = bezierCurve.getY(timeValue) * ((double) maxWidth);
        height = bezierCurve.getY(timeValue) * ((double) maxHeight);

        setPortalsSize(width, height);
        if (width < 0.1) {
            setNullShape();
        } else {
            if (width < 0.3) {
                numTriangles = 15;
            } else if (width < 0.11) {
                numTriangles = 20;
            } else {
                numTriangles = 50;
            }
            PortalFunctionUtil.makeRoundPortal(thePortal, numTriangles);
            PortalFunctionUtil.makeRoundPortal(result, numTriangles);
        }
        reloadPortals();
    }
    public void spawnRandomLightningEffect() {
        Entity entity = Devilrycraft.SMALL_DIRECTIONAL_LIGHTNING_ENTITY.create(world);
        entity.setPos(pos.getX() + 2 * random.nextDouble() - random.nextDouble(), pos.getY() + 2 * random.nextDouble() - random.nextDouble(), pos.getZ() + random.nextDouble() - random.nextDouble());
        world.spawnEntity(entity);
    }
    @Override
    public void tick() {

        if(ticksSinceCreation == 24)
        {
            this.getWorld().playSound(this.getPos().getX(), getPos().getY(), getPos().getZ(),
                    Devilrycraft.CHAOS_PORTAL, SoundCategory.BLOCKS, 1F, 1F, true
            );
            System.out.println("runTicks");
        }
        if (!this.getWorld().isClient()) {

            if (random.nextInt(LIGHTNING_SPAWN_CHANCE) == 1)
                spawnRandomLightningEffect();
            ticksSinceCreation++;

            if (this.hasPortals) {

                if (ticksSinceCreation > TICKS_TO_DESTROY - (PORTAL_ANIMATION_TICKS) && !(ticksSinceCreation > TICKS_TO_DESTROY - 2)) {
                    portalDestructionAnimationTicks--;
                    timeValue = portalDestructionAnimationTicks / PORTAL_ANIMATION_TICKS;
                    animatePortals(timeValue);
                }
                if (ticksSinceCreation > TICKS_TO_DESTROY)
                    destroyItself();
                if (ticksSinceCreation == 1) {
                    thePortal.world.spawnEntity(thePortal);
                    result = PortalManipulation.completeBiWayPortal(thePortal, Portal.entityType);
                    doSecondSound = true;
                    //thePortal.commandsOnTeleported = new ArrayList<>();
                    //thePortal.commandsOnTeleported.add("playsound devilry_craft:chaos_portal block "+resultPos.getX()+" "+resultPos.getY()+" "+resultPos.getZ()+" 1 1");
                }


                if(ticksSinceCreation == 4)
                {
                    playSpawnInSounds();
                }
                if (ticksSinceCreation < PORTAL_ANIMATION_TICKS && ticksSinceCreation > 2) {
                    timeValue = ((double) ticksSinceCreation + PORTAL_OFFSET_ANIMATION_TICKS) / (PORTAL_ANIMATION_TICKS + PORTAL_OFFSET_ANIMATION_TICKS);
                    width = bezierCurve.getY(timeValue) * maxWidth;
                    height = bezierCurve.getY(timeValue) * maxHeight;
                    animatePortals(timeValue);
                }
            }
        }

    }
}
