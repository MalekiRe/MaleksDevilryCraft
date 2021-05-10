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


    public SealFireHelper(String id) {
        super(id, CrystalType.FIRE_TYPE, CrystalType.FIRE_TYPE, CrystalType.FIRE_TYPE, CrystalType.FIRE_TYPE);
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
    public void doHelperTick() {

    }

    @Override
    public void doHelperTick(SealBlockEntity blockEntity) {
        performAttackAllMob();
    }

    @Override
    public void doHelperOneOffFunction() {

    }

    @Override
    public void doHelperOneOffFunction(SealBlockEntity blockEntity) {
        setHelperFields(blockEntity);

    }
}
