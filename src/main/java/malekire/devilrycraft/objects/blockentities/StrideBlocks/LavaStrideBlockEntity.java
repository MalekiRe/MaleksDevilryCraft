package malekire.devilrycraft.objects.blockentities.StrideBlocks;

import malekire.devilrycraft.common.DevilryBlockEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;
import malekire.devilrycraft.objects.blocks.StrideBlocks.LavaStride;


public class LavaStrideBlockEntity extends BlockEntity implements Tickable {

    public LavaStrideBlockEntity() {
        super(DevilryBlockEntities.LAVA_STRIDE_BLOCK_ENTITY);
    }


    int lavaTicksSinceCreation = 0;


    public void tick() {
        if(!getCachedState().get(LavaStride.PERSISTENT)) {
            if (lavaTicksSinceCreation > 20) {
                this.world.setBlockState(getPos(), Blocks.LAVA.getDefaultState(), 2);
            }
            ++lavaTicksSinceCreation;
        }
    }
}