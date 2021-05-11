package malekire.devilrycraft.objects.blockentities.sealhelpers;

import malekire.devilrycraft.util.CrystalType;
import malekire.devilrycraft.util.CrystalType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.ArrayList;
import java.util.List;

public class SealFireHelper extends AbstractSealHelperClass{


    public SealFireHelper() {
        super("attack_mobs_with_fire", CrystalType.FIRE_TYPE, CrystalType.FIRE_TYPE, CrystalType.FIRE_TYPE, CrystalType.FIRE_TYPE);
    }

    public void performAttackAllMob()
    {
        int i;
        long posX = this.pos.getX();
        long posY = this.pos.getY();
        long posZ = this.pos.getZ();
        Box flame = new Box(posX-2, posY-2, posZ-2, posX+2, posY+2, posZ+2);

        assert this.world != null;
        List<Entity> entitiesToFlame = this.world.getOtherEntities(null, flame, (entity) -> !(entity instanceof PlayerEntity) && entity.isPushable() && !(entity instanceof ItemEntity));
        for(i=0;i<entitiesToFlame.size();i++){
            entitiesToFlame.get(i).setOnFireFor(100);
        }
    }
    @Override
    public void tick() {
        performAttackAllMob();
    }

    @Override
    public void oneOffTick() {

    }


    @Override
    public AbstractSealHelperClass getNewInstance() {
        return new SealFireHelper();
    }
}
