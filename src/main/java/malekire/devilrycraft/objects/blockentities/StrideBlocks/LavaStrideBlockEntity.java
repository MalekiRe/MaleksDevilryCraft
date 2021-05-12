package malekire.devilrycraft.objects.blockentities.StrideBlocks;

import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;

public class LavaStrideBlockEntity extends BlockEntity {

    public LavaStrideBlockEntity() {
        super(DevilryBlockEntities.LAVA_STRIDE_BLOCK_ENTITY);
    }

    private int strideTick;
    public void tick() {
        if(strideTick>20){
            this.world.setBlockState(getPos(), Blocks.LAVA.getDefaultState(), 2);
        }
        strideTick++;
    }

}
