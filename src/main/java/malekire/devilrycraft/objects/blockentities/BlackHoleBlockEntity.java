package malekire.devilrycraft.objects.blockentities;

import malekire.devilrycraft.Devilrycraft;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Tickable;
import org.apache.logging.log4j.Level;

import static malekire.devilrycraft.common.DevilryBlockEntities.BLACK_HOLE_BLOCK_ENTITY;

public class BlackHoleBlockEntity extends BlockEntity implements Tickable {
    public BlackHoleBlockEntity() {
        super(BLACK_HOLE_BLOCK_ENTITY);
    }

    @Override
    public void tick() {
        Devilrycraft.LOGGER.log(Level.INFO, "HELLO WORLD");
    }
}
