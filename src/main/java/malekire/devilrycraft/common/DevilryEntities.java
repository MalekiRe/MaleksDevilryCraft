package malekire.devilrycraft.common;

import malekire.devilrycraft.objects.entities.SlimeZombieEntity;
import malekire.devilrycraft.objects.entities.SmallDirectionalLightningEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DevilryEntities {
    public static final EntityType<SmallDirectionalLightningEntity> SMALL_DIRECTIONAL_LIGHTNING_ENTITY;
    public static final EntityType<SlimeZombieEntity> SLIME_ZOMBIE_ENTITY_TYPE;
    static {
    SMALL_DIRECTIONAL_LIGHTNING_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("devilry_craft", "small_directional_lightning"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SmallDirectionalLightningEntity::new).dimensions(EntityDimensions.fixed(0f, 0f)).trackRangeBlocks(16).trackedUpdateRate(Integer.MAX_VALUE).build()
    );
    SLIME_ZOMBIE_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("devilry_craft", "slime_zombie_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimeZombieEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
    }
}
