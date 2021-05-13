package malekire.devilrycraft.objects.blockentities.sealhelpers;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
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
        int flameEntityTick;
        long posX = this.getPos().getX();
        long posY = this.getPos().getY();
        long posZ = this.getPos().getZ();
        Box flame = new Box(posX-2, posY-2, posZ-2, posX+2, posY+2, posZ+2);

        assert this.getWorld() != null;
        List<Entity> entitiesToFlame = this.getWorld().getOtherEntities(null, flame, (entity) -> !(entity instanceof PlayerEntity) && entity.isPushable() && !(entity instanceof ItemEntity));
        for(flameEntityTick=0;flameEntityTick<entitiesToFlame.size();flameEntityTick++){
            entitiesToFlame.get(flameEntityTick).setOnFireFor(100);
        }
    }

    @Override
    public void render(VertexConsumerProvider vertexConsumerProvider, MatrixStack matrixStack, int light) {

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
