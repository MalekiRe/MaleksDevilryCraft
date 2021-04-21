package malekire.devilrycraft.util.portalutil;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.PehkuiInterface;
import com.qouteall.immersive_portals.portal.Portal;
import malekire.devilrycraft.Devilrycraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Portal.class)
public abstract class PortableHolePortal {
    private boolean playSecondSound = true;
    @Shadow
    public RegistryKey<World> dimensionTo;
    @Shadow
    public Vec3d destination;
    @Shadow
    public List<String> commandsOnTeleported;
    @Shadow
    boolean teleportChangesScale;
    @Shadow
    public abstract World getDestWorld();
    @Shadow
    public Vec3d getDestPos() {
        return this.destination;
    }

    @Shadow
    public abstract Vec3d transformLocalVec(Vec3d localVec);
    @Shadow
    public abstract Vec3d transformLocalVecNonScale(Vec3d localVec);
    /**
     * @author MalekiRe
     */
    @Overwrite
    public void transformVelocity(Entity entity) {
        if (PehkuiInterface.isPehkuiPresent) {
            if (this.teleportChangesScale) {
                entity.setVelocity(this.transformLocalVecNonScale(entity.getVelocity()));
            } else {
                entity.setVelocity(this.transformLocalVec(entity.getVelocity()));
            }
        } else {
            entity.setVelocity(this.transformLocalVec(entity.getVelocity()));
        }


        if (entity.getVelocity().length() > 15.0D) {
            entity.setVelocity(entity.getVelocity().normalize().multiply(15.0D));
        }

        if (entity instanceof AbstractMinecartEntity && entity.getVelocity().lengthSquared() < 0.5D) {
            entity.setVelocity(entity.getVelocity().multiply(2.0D));
        }
        System.out.println("SDFHSDJHFSJDHFJSDFH");
        if(playSecondSound)
        {
            this.getDestWorld().playSound(null, new BlockPos(this.getDestPos()), Devilrycraft.CHAOS_PORTAL, SoundCategory.BLOCKS, 1F, 1F);
            playSecondSound = false;
            System.out.println("SDFHSDJHFSJDHFJSDFH");
        }


    }
}
