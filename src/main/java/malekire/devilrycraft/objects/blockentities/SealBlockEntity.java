package malekire.devilrycraft.objects.blockentities;


import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Tickable;

public class SealBlockEntity extends BlockEntity implements Tickable {
    public SealBlockEntity() {
        super(DevilryBlockEntities.SEAL_BLOCK_ENTITY);
    }

    @Override
    public void tick() {
        if(!world.isClient)
        {
            System.out.println(world.getBlockState(pos));
        }
    }
}
