package malekire.devilrycraft.objects.entities;

import malekire.devilrycraft.util.EntityPacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class SlimeZombieEntity extends ZombieEntity {


    public SlimeZombieEntity(EntityType<? extends SlimeZombieEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    protected void updatePostDeath() {
        super.updatePostDeath();
        if(this.deathTime == 20) {
            for(int i = 0; i < 4; i++) {
                ZombieEntity zombieEntity = new ZombieEntity(world);
                zombieEntity.setPos(getPos().x, getPos().y, getPos().z);
                zombieEntity.setBaby(true);
                this.world.spawnEntity(zombieEntity);
            }
            System.out.println("SPAWN BABY ZOMBIES");
        }
    }
}
