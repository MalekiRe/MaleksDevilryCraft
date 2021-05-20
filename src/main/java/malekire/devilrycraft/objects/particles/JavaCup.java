package malekire.devilrycraft.objects.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;

public class JavaCup extends SpriteBillboardParticle {
    public JavaCup(ClientWorld clientWorld){
        super(clientWorld, 0, 0, 0);
    }
    @Override
    public ParticleTextureSheet getType() {
        return null;
    }
    @Environment(EnvType.CLIENT)
    public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider provider;

        public DefaultFactory(SpriteProvider provider) {
            this.provider = provider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double r, double g, double b) {
            return new JavaCup(world);
        }
    }
}
