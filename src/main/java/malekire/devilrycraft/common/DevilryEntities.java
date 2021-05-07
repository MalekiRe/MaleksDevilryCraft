package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.entities.SmallDirectionalLightningEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DevilryEntities {
    public static final EntityType<SmallDirectionalLightningEntity> SMALL_DIRECTIONAL_LIGHTNING_ENTITY;

    static {
    SMALL_DIRECTIONAL_LIGHTNING_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("devilry_craft", "small_directional_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SmallDirectionalLightningEntity::new).dimensions(EntityDimensions.fixed(0f, 0f)).trackRangeBlocks(16).trackedUpdateRate(Integer.MAX_VALUE).build()
    );
    }
}
