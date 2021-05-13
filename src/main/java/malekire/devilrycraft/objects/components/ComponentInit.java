package malekire.devilrycraft.objects.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactory;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.util.Identifier;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class ComponentInit implements WorldComponentInitializer {
    public static final ComponentKey<SealMateWorldComponent> SEAL_MATE_WORLD_COMPONENT_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "seal_mate_world_component"), SealMateWorldComponent.class);
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(SEAL_MATE_WORLD_COMPONENT_COMPONENT_KEY, SealMateWorldComponent::new);
    }
}
