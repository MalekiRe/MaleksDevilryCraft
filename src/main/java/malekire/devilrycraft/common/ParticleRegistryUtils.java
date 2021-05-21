package malekire.devilrycraft.common;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

import static malekire.devilrycraft.Devilrycraft.DevilryID;

public class ParticleRegistryUtils {

    public static DefaultParticleType registerParticles(String leafParticleName) {


        return Registry.register(
                Registry.PARTICLE_TYPE,
                DevilryID(leafParticleName),
                FabricParticleTypes.simple(true)
        );
    }
}
