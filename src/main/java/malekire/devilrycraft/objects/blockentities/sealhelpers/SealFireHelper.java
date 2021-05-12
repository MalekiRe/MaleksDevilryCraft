package malekire.devilrycraft.objects.blockentities.sealhelpers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.List;

import static malekire.devilrycraft.objects.blockentities.sealhelpers.SealUtilities.FireSealID;

public class SealFireHelper extends AbstractSealHelper {


    public SealFireHelper() {
        super(FireSealID, FireSealID.sealCombinations);
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
    public AbstractSealHelper getNewInstance() {
        return new SealFireHelper();
    }
}
