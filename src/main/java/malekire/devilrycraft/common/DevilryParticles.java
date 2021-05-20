package malekire.devilrycraft.common;


import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

import static malekire.devilrycraft.Devilrycraft.DevilryID;

public class DevilryParticles {
    public static ArrayList<ParticleRegistryHelper> particles = new ArrayList<>();

    public static final ParticleType JavaPot = new DefaultParticleType(false){};
    static {
        add(JavaPot, "java_cup");
    }
    public static void add(ParticleType particle2, String name){
        particles.add(new ParticleRegistryHelper(particle2, DevilryID(name)));
    }
    public static void registerParticles(){
        for(ParticleRegistryHelper particle: DevilryParticles.particles){
            Registry.register(Registry.PARTICLE_TYPE, particle.identifier, particle.particle);
        }
    }

}
class ParticleRegistryHelper {
    public final ParticleType particle;
    public final Identifier identifier;
    public ParticleRegistryHelper(ParticleType particle, Identifier identifier){
        this.particle = particle;
        this.identifier = identifier;
    }

}
