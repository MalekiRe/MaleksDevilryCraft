package malekire.devilrycraft.objects.components;

import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.util.Identifier;

import static malekire.devilrycraft.Devilrycraft.MOD_ID;

public class ComponentInit implements WorldComponentInitializer, BlockComponentInitializer {
    public static final ComponentKey<SealMateWorldComponent> SEAL_MATE_WORLD_COMPONENT_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "seal_mate_world_component"), SealMateWorldComponent.class);
    public static final ComponentKey<SealBlockPosComponent> SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(MOD_ID, "seal_position_component"), SealBlockPosComponent.class);
    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(SEAL_MATE_WORLD_COMPONENT_COMPONENT_KEY, SealMateWorldComponent::new);
    }

    /**
     * Called to register component factories for statically declared component types.
     *
     * <p><strong>The passed registry must not be held onto!</strong> Static component factories
     * must not be registered outside of this method.
     *
     * @param registry a {@link BlockComponentFactoryRegistry} for <em>statically declared</em> components
     */
    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
        //registry.registerFor(SealBlockEntity.class, SEAL_BLOCK_POS_COMPONENT_COMPONENT_KEY,  SealBlockPosComponent::new);
    }
}
